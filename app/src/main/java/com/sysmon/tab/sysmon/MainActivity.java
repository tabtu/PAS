package com.sysmon.tab.sysmon;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.io.InputStream;

public class MainActivity extends Activity {

    private int MdfySS = 136;
    private int MAX_BUFFER = 2048;
    private Button btn_connect;
    private Button btn_gtlist;
    private EditText et_ip;
    private TextView tv_status;

    private boolean isConnecting = false;

    private Thread mThread = null;
    private Socket mSocket = null;
    //static InputStream minps = null;
    static BufferedReader mBufferedReader	= null;
    static PrintWriter mPrintWriter = null;
    private  String recvMessage = "";

    private List<SerMod> servlist = new ArrayList<SerMod>();

    private Handler tHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                tv_status.setText("Received " + (recvMessage.length() - 1) + " bytes\n" + "-->Msg: " + recvMessage + "\n" + tv_status.getText().toString());
            }
            else if(msg.what == -1)
            {
                tv_status.setText("Received Server Data " + (recvMessage.length() - 1) + " bytes\n");
                btn_gtlist.setEnabled(true);
            }
            else if(msg.what == 1)
            {
                tv_status.setText("IP address cannot be empty!!!\n");
            }
            else if(msg.what == 2)
            {
                tv_status.setText("Fake IP Address!!!\n");
            }
            else if(msg.what == 3)
            {
                tv_status.setText("Connected Succed!!!\n");
            }
            else if(msg.what == 4)
            {
                tv_status.setText("IP Error!!!\n" + recvMessage + "\n");
            }
            else
            {
                tv_status.setText("Received Error：" + recvMessage + "\n" + tv_status.getText().toString());
            }
        }
    };

    private Runnable mRunnable = new Runnable()
    {
        public void run()
        {
            String msgText = et_ip.getText().toString();
            if(msgText.length() <= 0)
            {
                Message msg = new Message();
                msg.what = 1;
                tHandler.sendMessage(msg);
                return;
            }

            int start = msgText.indexOf(":");
            if( (start == -1) ||(start + 1 >= msgText.length()) )
            {
                Message msg = new Message();
                msg.what = 2;
                tHandler.sendMessage(msg);
                return;
            }

            String sIP = msgText.substring(0, start);
            String sPort = msgText.substring(start + 1);
            int port = Integer.parseInt(sPort);
            try
            {
                mSocket = new Socket(sIP, port);
                //minps = mSocket.getInputStream();
                mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
                Message msg = new Message();
                msg.what = 3;
                tHandler.sendMessage(msg);
            }
            catch (Exception e)
            {
                recvMessage = e.getMessage() + "\n";
                Message msg = new Message();
                msg.what = 4;
                tHandler.sendMessage(msg);
                return;
            }

            char[] buffer = new char[MAX_BUFFER];
            int count = 0;
            while (isConnecting)
            {
                try
                {
                    if((count = mBufferedReader.read(buffer)) > 0) {

                        if (count % MdfySS == 2) {
                            byte[] byteData = getBytes(buffer);
                            //byteData = getBytes(buffer);
                            //int len = minps.read(byteData);  // 136byte for each, '\0' in the last
                            servlist = new ArrayList<SerMod>();
                            int ctmp = count / MdfySS;
                            for (int i = 0; i < ctmp; i++) {
                                byte[] btmp = new byte[MdfySS];
                                System.arraycopy(byteData, i * MdfySS, btmp, 0, MdfySS);
                                SerMod tmp = new SerMod(btmp);
                                servlist.add(tmp);
                            }
                        }


                        recvMessage = getInfoBuff(buffer, count) + "\n";
                        Message msg = new Message();
                        if (servlist.size() > 0) msg.what = -1;
                        else msg.what = 0;
                        tHandler.sendMessage(msg);
                    }
                }
                catch (Exception e)
                {
                    recvMessage = e.getMessage() + "\n";
                    Message msg = new Message();
                    msg.what = -2;
                    tHandler.sendMessage(msg);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_ip = (EditText)findViewById(R.id.et_ip);
        tv_status = (TextView)findViewById(R.id.tv_status);
        btn_connect = (Button)findViewById(R.id.btn_connect);
        btn_gtlist = (Button)findViewById(R.id.btn_list);

        btn_connect.setEnabled(true);
        btn_gtlist.setEnabled(false);

        et_ip.setText("222.222.222.3:18888");

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (isConnecting)
                {
                    isConnecting = false;
                    try {
                        if(mSocket != null)
                        {
                            mSocket.close();
                            mSocket = null;
                            mPrintWriter.close();
                            mPrintWriter = null;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mThread.interrupt();
                    btn_connect.setText("Connect");
                    et_ip.setEnabled(true);
                }
                else
                {
                    isConnecting = true;
                    btn_connect.setText("Disconnect");
                    et_ip.setEnabled(false);
                    mThread = new Thread(mRunnable);
                    mThread.start();
                }
            }
        });

        btn_gtlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String msg = "";
                for (int i = 0; i < servlist.size(); i++) {
                    msg += (servlist.get(i).ToStringExt() + "/");
                }
                Intent intent = new Intent();
                intent.putExtra("servlist", msg);
                intent.setClass(MainActivity.this, ServActivity.class);
                startActivity(intent);

            }
        });
    }

    private byte[] getBytes (char[] chars) {
        Charset cs = Charset.forName ("ASCII");
        CharBuffer cb = CharBuffer.allocate (chars.length);
        cb.put (chars);
        cb.flip ();
        ByteBuffer bb = cs.encode (cb);

        return bb.array();

    }

    private char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("ASCII");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

        return cb.array();
    }

    private String getInfoBuff(char[] buff, int count) {
        char[] temp = new char[count];
        for(int i = 0; i < count; i++)
        {
            temp[i] = buff[i];
        }
        return new String(temp);
    }

    public void onDestroy() {
        super.onDestroy();
        if (isConnecting)
        {
            isConnecting = false;
            try {
                if(mSocket != null)
                {
                    mSocket.close();
                    mSocket = null;
                    mPrintWriter.close();
                    mPrintWriter = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mThread.interrupt();
        }
    }

}
