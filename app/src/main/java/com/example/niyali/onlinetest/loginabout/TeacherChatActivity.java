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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.MsgAdapter;
import com.example.niyali.onlinetest.bean.Msg;
import com.example.niyali.onlinetest.bean.MyBmobInstallation;
import com.example.niyali.onlinetest.json.JsonHelper;
import com.example.niyali.onlinetest.push.PushActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by niyali on 17/4/23.
 */

public class TeacherChatActivity extends AppCompatActivity {
    private TextView titlebar;
    private ListView listView;
    private BmobPushManager bmobPushManager;
    private String target_id;
    private IntentFilter intentFilter;
    public static final String TAG="TeacherChatActivity";
    private ChatMessageReceiver chatMessageReceiver;
    private ArrayAdapter<String> adapter;
    private List<String> students;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chatlist_show);
        pref=getSharedPreferences("chat",MODE_PRIVATE);
        Map<String,?> allChat=pref.getAll();

        titlebar = (TextView) findViewById(R.id.titlebar);
        titlebar.setText("在线答疑");
        listView = (ListView) findViewById(R.id.chat_list);
        students=new ArrayList<String>();
        //students.add("nini");
        for(String str:allChat.keySet())
        {
            String temp[]=str.split("@");
            if(!students.contains(temp[2]))
            {
                students.add(temp[2]);
            }
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction("cn.bmob.push.action.MESSAGE");
        chatMessageReceiver = new ChatMessageReceiver();
        registerReceiver(chatMessageReceiver, intentFilter);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,students);
        listView.setAdapter(adapter);
        //更新设备信息
        BmobQuery<MyBmobInstallation> query=new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
        query.findObjects(this, new FindListener<MyBmobInstallation>() {
            @Override
            public void onSuccess(List<MyBmobInstallation> list) {
                if (list.size()>0){
                    MyBmobInstallation my=list.get(0);
                    my.setUserName(BmobUser.getCurrentUser(TeacherChatActivity.this).getUsername());
                    my.update(TeacherChatActivity.this, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(TeacherChatActivity.this,"install表更新成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });



        editor=getSharedPreferences("chat",MODE_PRIVATE).edit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String studentName=students.get(position);
                Intent teacher=new Intent(TeacherChatActivity.this,TeacherSingleChatAcitivity.class);
                //把学生名字传递过去
                teacher.putExtra("name",studentName);
                startActivity(teacher);
            }
        });
        ExitApplication.getInstance().addActivity(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chatMessageReceiver);
    }


    public class ChatMessageReceiver extends BroadcastReceiver {

        SimpleDateFormat sdf;
        //收到的消息
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
                Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
                Toast.makeText(TeacherChatActivity.this,intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();
                
                String receivedMsg=intent.getStringExtra("msg");
                JsonObject jsonObject=new JsonParser().parse(receivedMsg).getAsJsonObject();
                JsonObject jsonObjReceive=new JsonParser().parse(jsonObject.get("alert").getAsString()).getAsJsonObject();
                String msgContext=jsonObjReceive.get("context").getAsString();
                String studentname=jsonObjReceive.get("userName").getAsString();
                String installId=jsonObjReceive.get("installId").getAsString();
                if(!students.contains(studentname))
                {
                    students.add(studentname);
                }
                adapter.notifyDataSetChanged();
                sdf=new SimpleDateFormat("HH:mm:ss");
                String now=sdf.format(new Date());
                editor.putInt(now+"@"+msgContext+"@"+studentname,Msg.TYPE_RECEIVED);
                editor.commit();

            }
        }

    }

}
