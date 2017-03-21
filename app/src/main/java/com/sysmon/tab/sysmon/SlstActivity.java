package com.sysmon.tab.sysmon;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SlstActivity extends Activity {

    private List<SerMod> servlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv);

        ArrayList<String> prefix = getIntent().getStringArrayListExtra("servlist");
        servlist = new ArrayList<SerMod>();
        for (int i = 0; i < prefix.size(); i++)
        {
            SerMod ss = new SerMod(prefix.get(i));
            servlist.add(ss);
        }

        List<String> data = new ArrayList<String>();
        for (int i = 0; i < servlist.size(); i++)
        {
            //servlist.get(i).ToStringExt();
            if (i == 0) data.add("Master");
            else data.add("Branch: " + servlist.get(i).DISK_fr + ":" + servlist.get(i).DISK_us);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SlstActivity.this,android.R.layout.simple_list_item_1, data);


        //ServAdapter adapter;
        //adapter = new ServAdapter(ServsActivity.this, servlist);
        ListView listView=(ListView) findViewById(R.id.lv_serv);
        listView.setAdapter(adapter);



        /*
        listview.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //我们需要的内容，跳转页面或显示详细信息
            }
        });
        */

    }
}
