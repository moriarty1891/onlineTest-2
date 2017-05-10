package com.example.niyali.onlinetest.loginabout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.ExamAdapter;
import com.example.niyali.onlinetest.adapter.RecordAdapter;
import com.example.niyali.onlinetest.bean.Record;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by niyali on 17/4/28.
 */

public class RecordActivity extends AppCompatActivity {
    private TextView titlebar;
    private SharedPreferences pref;
    private List<Record> records;
    private ListView listView;
    public static final String TAG="RecordActivity";
    public static final int DATA_QUERY=1;
    private android.os.Handler mHandler=new android.os.Handler(){
        public void handleMessage(Message msg){
            if(msg.what==DATA_QUERY)
            {
                List<Record> real=(List<Record>)msg.obj;
                records=real;
                Log.i(TAG, "handleMessage: "+records.size()+records.get(0).getTextName()+records.get(0).getTime());
                RecordAdapter adapter=new RecordAdapter(RecordActivity.this,R.layout.each_record,records);
                listView.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.record_list);
        titlebar=(TextView)findViewById(R.id.titlebar);
        listView=(ListView)findViewById(R.id.record_list);
        titlebar.setText("练习历史");
        queryFromServer();
        Log.i(TAG, "records: "+records.toString());
        ExitApplication.getInstance().addActivity(this);
    }
    public void queryFromServer()
    {
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        String name=pref.getString("username","lili");
        records=new ArrayList<Record>();
        BmobQuery<Record> query=new BmobQuery<Record>();
        query.addWhereEqualTo("username",name);
        query.findObjects(this, new FindListener<Record>() {
            @Override
            public void onSuccess(List<Record> recordList) {
                for(Record record:recordList)
                {

                    Toast.makeText(RecordActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                    records.add(record);
                    Log.i(TAG, "queryFromServer: "+records.size());
                }
                Message msg=new Message();
                msg.what=DATA_QUERY;
                msg.obj=records;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(RecordActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onError: "+s+String.valueOf(i));
            }
        });

    }
}
