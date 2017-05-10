package com.example.niyali.onlinetest.loginabout;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.MessageAdapter;
import com.example.niyali.onlinetest.bean.Message;
import com.example.niyali.onlinetest.json.JsonHelper;
import com.example.niyali.onlinetest.push.PushActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bmob.push.PushConstants;

/**
 * Created by niyali on 17/5/5.
 */

public class ExitApplication extends Application{

    public static List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;
    private static final String TAG="ExitApplication";

//    public List<Message> getMessagelist() {
//        return messagelist;
//    }
//
//    private List<Message> messagelist=new ArrayList<Message>();
//    private PushMessageReceiver pushMessageReceiver;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //动态注册广播接收器,启动后才能收到广播;
//        IntentFilter intentFilter=new IntentFilter();
//        intentFilter.addAction("cn.bmob.push.action.MESSAGE");
//        pushMessageReceiver=new PushMessageReceiver();
//        registerReceiver(pushMessageReceiver,intentFilter);
//    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        unregisterReceiver(pushMessageReceiver);
//    }


    //单例模式中获取唯一的ExitApplication实例
    public static ExitApplication getInstance()
    {
        if(null == instance)
        {
            instance = new ExitApplication();
        }
        return instance;

    }
    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
        Log.i(TAG, "addActivity: "+activity.toString());
        Log.i(TAG, "activity: "+activityList.toString());
    }
    //遍历所有Activity并finish

    public void exit()
    {

        for(Activity activity:activityList)
        {

            if (activity.getClass().getSimpleName().equals("MainActivity"))
            {
                continue;
            }
            activity.finish();
            Log.i(TAG, "exit: "+activity.getClass().getSimpleName());
        }

        //System.exit(0);

    }
//    public class PushMessageReceiver extends BroadcastReceiver {
//        private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
//                Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
//                Toast.makeText(getApplicationContext(),intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();
//                String pushString=intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
//                Map<String,String> messageMap= JsonHelper.parseJson(pushString);
//                String currentTime=simpleDateFormat.format(new Date());
//                String title=messageMap.get("title");
//                String message=messageMap.get("message");
//                SharedPreferences pref=getSharedPreferences("push",MODE_PRIVATE);
//                SharedPreferences.Editor editor=pref.edit();
//                editor.putString(currentTime+"@"+title,message);
//                editor.commit();
//                Message tempMessage=new Message(title,message,currentTime);
//                messagelist.add(tempMessage);
//
//            }
//        }
//
//    }
}

