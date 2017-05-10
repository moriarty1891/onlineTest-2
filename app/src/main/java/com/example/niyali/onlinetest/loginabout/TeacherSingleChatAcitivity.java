package com.example.niyali.onlinetest.loginabout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.MsgAdapter;
import com.example.niyali.onlinetest.bean.ChatSendMsg;
import com.example.niyali.onlinetest.bean.Msg;
import com.example.niyali.onlinetest.bean.MyBmobInstallation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by niyali on 17/5/5.
 */

public class TeacherSingleChatAcitivity extends AppCompatActivity {
    private TextView titlebar;
    private ListView listView;
    private Button send;
    private EditText input;
    BmobPushManager bmobPushManager;
    public static final int TARGET_ID=1;
    private String target_id;
    private MsgAdapter adapter;
    private List<Msg> msgList=new ArrayList<Msg>();
    private IntentFilter intentFilter;
    public static final String TAG="TeacherSingleChatActivity";
    private TeacherChatMessageReceiver chatMessageReceiver;
    private String studentName;
    private String myName;
    private SharedPreferences pref;
    private SharedPreferences sharedPreferences;
    private Map<String,?> allchat;
    private SharedPreferences.Editor editor;
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_chat_layout);
        titlebar=(TextView)findViewById(R.id.titlebar);
        listView=(ListView)findViewById(R.id.msg_list_view);
        send=(Button)findViewById(R.id.send);
        input=(EditText)findViewById(R.id.input_text);
        initMsg();
        Intent intent=getIntent();
        studentName=intent.getStringExtra("name");
        pref=getSharedPreferences("chat",MODE_PRIVATE);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        myName=sharedPreferences.getString("username","lili");
        allchat=pref.getAll();
        Map<String,Integer> sortedMap=sort(allchat);
        Log.i(TAG, "sortedMap: "+sortedMap.toString());
        editor=pref.edit();
        sdf=new SimpleDateFormat("HH:mm:ss");
        adapter=new MsgAdapter(this,R.layout.msg_item,msgList);
        listView.setAdapter(adapter);
        if(allchat.size()>0)
        {
            for(Map.Entry<String,Integer> entry:sortedMap.entrySet())
            {
                String temp[]=entry.getKey().split("@");
                if(temp[2].equals(studentName))
                {
                    Msg msg=new Msg(temp[1], entry.getValue());
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(msgList.size());
                }


            }
        }



        titlebar.setText(studentName);
        intentFilter=new IntentFilter();
        intentFilter.addAction("cn.bmob.push.action.MESSAGE");
        chatMessageReceiver=new TeacherChatMessageReceiver();
        registerReceiver(chatMessageReceiver,intentFilter);

        final android.os.Handler handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message message)
            {
                if (message.what==TARGET_ID)
                {
                    String targetTemp=(String)message.obj;
                    Log.i(TAG, "handleMessage: "+targetTemp);
                    target_id=targetTemp;
                }

            }

        };
        //得到用户信息
        BmobQuery<MyBmobInstallation> query=new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("userName",studentName);
        query.findObjects(this, new FindListener<MyBmobInstallation>() {
            @Override
            public void onSuccess(List<MyBmobInstallation> list) {
                Toast.makeText(TeacherSingleChatAcitivity.this,"成功",Toast.LENGTH_SHORT).show();
                if(list.size()>0)
                {
                    MyBmobInstallation my=list.get(0);
                    String targetId=my.getInstallationId();
                    Log.i(TAG, "onSuccess: "+targetId);
                    Message msg= new Message();
                    msg.what=TARGET_ID;
                    msg.obj=targetId;
                    handler.sendMessage(msg);

                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: "+i+s);

            }
        });
        //发送消息
        bmobPushManager=new BmobPushManager(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText=input.getText().toString().trim();
                String sendMsg=toJson(inputText);

                Log.i(TAG, "onClick: "+inputText);
                Log.i(TAG, "target_id: "+target_id);
                pushAndroidMessage(sendMsg,target_id);
                if (!"".equals(inputText)){
                    Msg msg =new Msg(inputText,Msg.TYPE_SEND);
                    msgList.add(msg);
                    editor.putInt(sdf.format(new Date())+"@"+inputText+"@"+studentName,Msg.TYPE_SEND);
                    editor.commit();
                    Log.i(TAG, "onClick: "+msgList.size());
                    adapter.notifyDataSetChanged();
                    listView.setSelection(msgList.size());
                    input.setText("");
                }


            }
        });
        ExitApplication.getInstance().addActivity(this);
    }



    private void pushAndroidMessage(String message,String installId)
    {
        BmobQuery<BmobInstallation> query=BmobInstallation.getQuery();
        query.addWhereEqualTo("installationId",installId);
        bmobPushManager.setQuery(query);
        bmobPushManager.pushMessage(message);
    }
    public void initMsg(){
        Msg msg1=new Msg("hello",Msg.TYPE_SEND);
        msgList.add(msg1);
        Msg msg2=new Msg("niyali",Msg.TYPE_RECEIVED);
        msgList.add(msg2);
    }
    public Map<String,Integer> sort(Map<String,?> obj)
    {
        Map<String,Integer> sortedMap=new TreeMap<String, Integer>();
        for(Map.Entry<String,?> entry:obj.entrySet())
        {
            sortedMap.put(entry.getKey(),(Integer) entry.getValue());
        }
        return sortedMap;
    }
    public String toJson(String text)
    {
        ChatSendMsg chatsendMsg=new ChatSendMsg();
        chatsendMsg.setContext(text);
        chatsendMsg.setUserName(myName);
        chatsendMsg.setInstallId(target_id);

//        JSONObject object=new JSONObject();
//        object.put();
//        object.toString();

        Gson gson=new Gson();

        return gson.toJson(chatsendMsg);

    }
    public class TeacherChatMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
                Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
                Toast.makeText(TeacherSingleChatAcitivity.this,intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();
                String receivedMsg=intent.getStringExtra("msg");
                JsonObject jsonObject=new JsonParser().parse(receivedMsg).getAsJsonObject();
                //JsonObject jsonObjReceive=jsonObject.get("alert").getAsJsonObject();
                JsonObject jsonObjReceive=new JsonParser().parse(jsonObject.get("alert").getAsString()).getAsJsonObject();
                String msgContext=jsonObjReceive.get("context").getAsString();
                String name=jsonObjReceive.get("userName").getAsString();
                String installId=jsonObjReceive.get("installId").getAsString();
                editor.putInt(sdf.format(new Date())+"@"+msgContext+"@"+studentName,Msg.TYPE_RECEIVED);
                editor.commit();
                Msg msg=new Msg(msgContext,Msg.TYPE_RECEIVED);
                msgList.add(msg);
                adapter.notifyDataSetChanged();
                listView.setSelection(msgList.size());


            }
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chatMessageReceiver);
    }

}
