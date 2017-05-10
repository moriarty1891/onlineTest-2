package com.example.niyali.onlinetest.loginabout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by niyali on 17/4/3.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView titlebar;
    private EditText username;
    private EditText password;
    private Button realLogin;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        mContext=this;
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("Login");
        username=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password);
        realLogin=(Button)findViewById(R.id.reallogin);
        realLogin.setOnClickListener(this);
        ExitApplication.getInstance().addActivity(this);
    }
    @Override
    public void onClick(View v)
    {
        final String loginUserName=username.getText().toString();
        final String loginPassWord=password.getText().toString();
        if(TextUtils.isEmpty(loginUserName)||TextUtils.isEmpty(loginPassWord))
        {
            Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobUser oldUser=new BmobUser();
        oldUser.setPassword(loginPassWord);
        oldUser.setUsername(loginUserName);
        oldUser.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("username",loginUserName);
                editor.putString("password",loginPassWord);
                editor.commit();
                Intent intent=new Intent(LoginActivity.this,ExamShowActivity.class);
                intent.putExtra("from_activity",2);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
