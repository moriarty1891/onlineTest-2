package com.example.niyali.onlinetest.loginabout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by niyali on 17/4/3.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView titleBar;
    private EditText userName;
    private EditText passWord;
    private Button realSign;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);
        titleBar=(TextView)this.findViewById(R.id.titlebar);
        titleBar.setText("Sign up");
        userName=(EditText)findViewById(R.id.user);
        passWord=(EditText)findViewById(R.id.password);
        realSign=(Button)findViewById(R.id.sign);
        realSign.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        final String username=userName.getText().toString();
        final String password=passWord.getText().toString();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        BmobUser newUser=new BmobUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();
                Intent area =new Intent(SignUpActivity.this,ChooseAreaActivity.class);
                startActivity(area);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(SignUpActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        });


        ExitApplication.getInstance().addActivity(this);
    }
}
