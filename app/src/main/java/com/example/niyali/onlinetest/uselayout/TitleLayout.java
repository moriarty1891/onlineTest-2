package com.example.niyali.onlinetest.uselayout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.loginabout.BookActivity;
import com.example.niyali.onlinetest.loginabout.ChatActivity;
import com.example.niyali.onlinetest.loginabout.ExamShowActivity;
import com.example.niyali.onlinetest.loginabout.ExitApplication;
import com.example.niyali.onlinetest.loginabout.InfoActivity;
import com.example.niyali.onlinetest.loginabout.TeacherChatActivity;
import com.example.niyali.onlinetest.push.PushActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niyali on 17/4/15.
 */

public class TitleLayout extends LinearLayout {
    private SharedPreferences pref;
    private static final String TAG="TitleLayout";

    public TitleLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.footbar,this);
        RadioButton func1=(RadioButton)findViewById(R.id.func1);
        final RadioButton func2=(RadioButton)findViewById(R.id.func2);
        RadioButton test=(RadioButton)findViewById(R.id.func3);
        RadioButton func4=(RadioButton)findViewById(R.id.func4);
        RadioButton func5=(RadioButton)findViewById(R.id.func5);
        pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        final String loginname=pref.getString("username","lili");
        //消息推送
        func5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ExitApplication.getInstance().getMessagelist().clear();

                Intent push=new Intent(getContext(), PushActivity.class);
                Log.i(TAG, "pushonClick: "+getContext().toString());
                context.startActivity(push);
                judgeChat();


            }
        });
//        func5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                func5.setChecked(true);
//                ExitApplication.getInstance().getMessagelist().clear();
//                Intent push=new Intent(getContext(), PushActivity.class);
//                context.startActivity(push);
//            }
//        });
        //题库 逻辑跳转 显示当前用户选择的试题页面 未做判断
        test.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test=new Intent(getContext(), ExamShowActivity.class);
                getContext().startActivity(test);
            }
        });
        //书籍推荐
        func4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book=new Intent(getContext(), BookActivity.class);
                getContext().startActivity(book);
            }
        });
        //名师答疑
        func2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                func2.setChecked(true);

                if(loginname.equals("Amy"))
                {
                    Intent singlechat=new Intent(getContext(), TeacherChatActivity.class);
                    getContext().startActivity(singlechat);
                    judgePush();
                }
                else{
                    Intent chat =new Intent(getContext(),ChatActivity.class);
                    getContext().startActivity(chat);
                    judgePush();
                }

            }
        });
        func1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info=new Intent(getContext(),InfoActivity.class);
                getContext().startActivity(info);
            }
        });


    }
    public void judgeChat(){
        Pattern p1=Pattern.compile(".+ChatActivity");
        Matcher matcher;
        List<Activity> lists=ExitApplication.activityList;
        Log.i(TAG, "judgebefore: "+lists.toString());
        for(Activity activity:lists)
        {
            String activityName=activity.getClass().getSimpleName();
            matcher=p1.matcher(activityName);
            if (matcher.matches())
            {
                activity.finish();
                //lists.remove(activity);
            }
        }
        Log.i(TAG, "judgeafter: "+lists.toString());

    }
    public void judgePush(){
        List<Activity> lists=ExitApplication.activityList;
        Log.i(TAG, "judgebefore: "+lists.toString());
        for(Activity activity:lists)
        {
            if (activity.getClass().getSimpleName().equals("PushActivity"))
            {
                activity.finish();
                //lists.remove(activity);
            }
        }
        Log.i(TAG, "judgeafter: "+lists.toString());

    }

}
