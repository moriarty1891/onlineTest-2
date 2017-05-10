package com.example.niyali.onlinetest.loginabout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.niyali.onlinetest.R;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

public class MainActivity extends AppCompatActivity {
    private Button signUp;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "3f7780c959d0dd9457f9d467b56a6274");
        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(MainActivity.this);
        signUp=(Button)findViewById(R.id.signup);
        login=(Button)findViewById(R.id.login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signupIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        ExitApplication.getInstance().addActivity(this);
    }
}
