package com.example.niyali.onlinetest.loginabout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.MyBmobInstallation;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by niyali on 17/4/15.
 */

public class ChatActivity extends AppCompatActivity {
    private TextView titlebar;
    private String[] data={"Amy","Bob","Clina","David","Emmy","Fury","Gina"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chatlist_show);
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("名师咨询");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ChatActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView=(ListView)findViewById(R.id.chat_list);
        listView.setAdapter(adapter);
        //更新设备信息
        BmobQuery<MyBmobInstallation> query=new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
        query.findObjects(this, new FindListener<MyBmobInstallation>() {
            @Override
            public void onSuccess(List<MyBmobInstallation> list) {
                if (list.size()>0){
                    MyBmobInstallation my=list.get(0);
                    my.setUserName(BmobUser.getCurrentUser(ChatActivity.this).getUsername());
                    my.update(ChatActivity.this, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ChatActivity.this,"install表更新成功",Toast.LENGTH_SHORT).show();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=data[position];
                Intent intent=new Intent(ChatActivity.this,SingleChatActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });


        ExitApplication.getInstance().addActivity(this);
    }
}
