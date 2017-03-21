package com.sysmon.tab.sysmon;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //String prefix = getIntent().getStringExtra("serv");
        //SerMod ss = new SerMod(prefix);

        //ArrayList<String> prefix = getIntent().getStringArrayListExtra("servlist");

        /*String[] smlist = prefix.split("/");
        List<SerMod> servlist = new ArrayList<SerMod>();
        for (int i = 0; i < smlist.length; i++)
        {
            SerMod ss = new SerMod(smlist[i]);
            servlist.add(ss);
        }*/

        WebView wv = (WebView)findViewById(R.id.wv_detail);

        WebSettings settings = wv.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setBlockNetworkImage(false);


        //wv.addJavascriptInterface(DetailActivity.this, "callByJs");


        //context.getClass().getClassLoader().getResourceAsStream("assets/"+资源名);
        wv.loadUrl("file:///android_asset/Chat.html");
        //wv.LoadUrl("javascript:alert('hello js')");
    }
}
