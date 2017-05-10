package com.example.niyali.onlinetest.loginabout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by niyali on 17/4/28.
 */

public class MyInfoActivity extends AppCompatActivity {
    private TextView titlebar;
    private SharedPreferences pref;
    private Button updatePwd;
    private TextView username;
    private EditText password;
    private String userName;
    private Context context;
    public static final String TAG="MyInfoActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myinfo_show);
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("我的个人信息");
        updatePwd=(Button)findViewById(R.id.update_password);
        username=(TextView)findViewById(R.id.show_username);
        password=(EditText)findViewById(R.id.show_password);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        userName=pref.getString("username","lili");
//        final String passWord=pref.getString("password","123456");
        username.setText(userName);
//        password.setText(passWord);

//        updatePwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BmobUser newUser=new BmobUser();
//                Log.i(TAG, "onClick: "+password.getText().toString());
//                newUser.setPassword(password.getText().toString());
//                BmobUser bmob=BmobUser.getCurrentUser(getApplicationContext());
//                Log.i(TAG, "onClick: "+bmob.getObjectId());
//                newUser.update(getApplicationContext(), bmob.getObjectId(), new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Log.i(TAG, "onFailure: "+i+s);
//                        Toast.makeText(context,"更新失败",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser=BmobUser.getCurrentUser(getApplicationContext());
                bmobUser.setPassword(password.getText().toString());
                bmobUser.update(getApplicationContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"更新失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        ExitApplication.getInstance().addActivity(this);

    }
}
