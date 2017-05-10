package com.example.niyali.onlinetest.loginabout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;

import cn.bmob.v3.BmobUser;

/**
 * Created by niyali on 17/4/28.
 */

public class InfoActivity extends AppCompatActivity {
    private TextView titlebar;
    private Button myInfo;
    private Button record;
    private Button logout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_main);
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("个人信息");
        myInfo=(Button)findViewById(R.id.my_info);
        record=(Button)findViewById(R.id.my_record);
        logout=(Button)findViewById(R.id.loginout);

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinfo=new Intent(InfoActivity.this,MyInfoActivity.class);
                startActivity(myinfo);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent record=new Intent(InfoActivity.this,RecordActivity.class);
                startActivity(record);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(InfoActivity.this);
                dialog.setTitle("退出登录");
                dialog.setMessage("确定要退出登录?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExitApplication.getInstance().exit();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });
        ExitApplication.getInstance().addActivity(this);
    }
}
