package com.example.niyali.onlinetest.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.MessageAdapter;
import com.example.niyali.onlinetest.bean.Message;
import com.example.niyali.onlinetest.json.JsonHelper;
import com.example.niyali.onlinetest.loginabout.ExitApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.bmob.push.PushConstants;
import okhttp3.internal.framed.PushObserver;

/**
 * Created by niyali on 17/4/15.
 */

public class PushActivity extends AppCompatActivity {
    private TextView titlebar;
    private IntentFilter intentFilter;
    private PushMessageReceiver pushMessageReceiver;
    private Map<String,String> messageMap;
    private MessageAdapter adapter;
    private List<Message> messagelist=new ArrayList<Message>();
    //private List<Message> messagelist;
    private ListView listView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        messagelist.clear();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push_layout);
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("消息推送");
        pref=getSharedPreferences("push",MODE_PRIVATE);
        editor=pref.edit();
        listView=(ListView)findViewById(R.id.push_list_view);
        Map<String,?> map=pref.getAll();
        Map<String,String> sortedMap=sorted(map);
        //messagelist = ExitApplication.getInstance().getMessagelist();
        if(map.size()>0)
        {

            for(Map.Entry<String,String> entry:sortedMap.entrySet())
            {
                String key=entry.getKey();
                String temp[]=key.split("@");
                String mess=entry.getValue();
                Message msg=new Message(temp[1],mess,temp[0]);
                messagelist.add(msg);
            }
        }
        //动态注册广播接收器,启动后才能收到广播;
        intentFilter=new IntentFilter();
        intentFilter.addAction("cn.bmob.push.action.MESSAGE");
        pushMessageReceiver=new PushMessageReceiver();
        registerReceiver(pushMessageReceiver,intentFilter);

        if (messagelist !=null) {
            adapter=new MessageAdapter(PushActivity.this,R.layout.push_each,messagelist);
        } else {
            adapter.notifyDataSetChanged();
        }

        listView.setAdapter(adapter);

        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pushMessageReceiver);
    }

    public Map<String,String> sorted(Map<String,?> obj)
    {
        Map<String,String> sortedMap=new TreeMap<String, String>();
        for(Map.Entry<String,?> entry:obj.entrySet())
        {
            sortedMap.put(entry.getKey(),(String) entry.getValue());
        }
        return sortedMap;
    }


    //在活动销毁前调用,保存临时数据
    //临时数据使用onSaveInstanceState保存恢复，永久性数据使用onPause方法保存。
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }

    public class PushMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
                Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
                Toast.makeText(PushActivity.this,intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();
                String pushString=intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
                messageMap=JsonHelper.parseJson(pushString);
                String currentTime=simpleDateFormat.format(new Date());
                String title=messageMap.get("title");
                String message=messageMap.get("message");
                editor.putString(currentTime+"@"+title,message);
                editor.commit();
                Message tempMessage=new Message(title,message,currentTime);
                messagelist.add(tempMessage);
                adapter.notifyDataSetChanged();
                listView.setSelection(messagelist.size());

            }
        }

    }
}
