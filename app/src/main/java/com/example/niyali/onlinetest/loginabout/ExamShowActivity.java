package com.example.niyali.onlinetest.loginabout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.ExamAdapter;
import com.example.niyali.onlinetest.bean.Exam;
import com.example.niyali.onlinetest.bean.Record;
import com.example.niyali.onlinetest.db.OnlineTestDB;
import com.example.niyali.onlinetest.db.OnlineTestOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by niyali on 17/4/9.
 * 用户登录时若此用户之前没有记录,则默认显示北京2015年试卷,若有记录 显示最新的记录
 */
//现在存在问题:第一次加载不成功 第二次的请求正常
public class ExamShowActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int UPDATE_DATA=1;
    public static final int TRANS_CODE=2;
    private ListView examListView;
    private List<Exam> examList;
    private List<Exam> displayExamList;
    private OnlineTestDB onlineTestDB;
    public static final String TAG="ExamShowActivity";
    private int fromWhere;
    private Button switchExam;
    private Button submit;
    private static int year_code;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private android.os.Handler mHandler=new android.os.Handler(){
        public void handleMessage(Message msg){
            if(msg.what==UPDATE_DATA)
            {
                ExamAdapter adapter=new ExamAdapter(ExamShowActivity.this,R.layout.each_test,displayExamList);
                examListView.setAdapter(adapter);
            }
            if(msg.what==TRANS_CODE)
            {
                int code=(Integer) msg.obj;
                Log.i(TAG, "handleMessage: "+code);
                year_code=code;
                queryExam(year_code);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        context=this;



        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exam_show);
        onlineTestDB= OnlineTestDB.getInstance(this);
        examListView=(ListView) findViewById(R.id.list_view_exam);

        switchExam=(Button)findViewById(R.id.change_exam);
        submit=(Button)findViewById(R.id.submit);
        switchExam.setOnClickListener(this);
        submit.setOnClickListener(this);
        displayExamList=new ArrayList<Exam>();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        //从区域页面跳转来的是1 从登陆页面来是2
        fromWhere=getIntent().getIntExtra("from_activity",0);
        if(fromWhere==2)
        {
            String name=pref.getString("username","lili");
            BmobQuery<Record> query=new BmobQuery<Record>();
            query.addWhereEqualTo("username",name);
            query.order("-updatedAt");
            query.setLimit(1);
            query.findObjects(this, new FindListener<Record>() {
                @Override
                public void onSuccess(List<Record> recordList) {
                    Toast.makeText(ExamShowActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                    if (recordList.size()>0){
                        Log.i(TAG, "----onSuccess: "+recordList.get(0).getYearCode());
                        year_code=recordList.get(0).getYearCode();
                    }
                    if(recordList.size()==0){
                        year_code=15;
                    }
                    editor=pref.edit();
                    editor.putInt("year_code",year_code);
                    editor.commit();
                    Message msg=new Message();
                    msg.what=TRANS_CODE;
                    msg.obj=year_code;
                    mHandler.sendMessage(msg);


                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(ExamShowActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onError: "+s+String.valueOf(i));
                }
            });

        }
        if(fromWhere!=2)
        {

            year_code=pref.getInt("year_code",15);
            queryExam(year_code);
        }
        Log.i(TAG, "finally: "+year_code);
        Log.i(TAG, "finally: "+fromWhere);

        ExitApplication.getInstance().addActivity(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.change_exam:
                Intent intent=new Intent(this,ChooseAreaActivity.class);
                startActivity(intent);
                break;
            case R.id.submit:
                Intent score=new Intent(this,ScoreActivity.class);
                startActivity(score);
                break;
        }
    }




    public void test(int year_code)
    {
        examList=onlineTestDB.loadExam(year_code);
        if(examList.size()>0)
        {
            Log.i(TAG, "queryExam:examList.size =  "+examList.size());
            displayExamList.clear();
            for(Exam exam1:examList)
            {
                String examContext=exam1.getExamContext();
                String first=exam1.getChoose_A();
                String second=exam1.getChoose_B();
                String third=exam1.getChoose_C();
                String fourth=exam1.getChoose_D();
                int examId=exam1.getId();
                Exam temp=new Exam(examContext,first,second,third,fourth,examId);
                displayExamList.add(temp);
            }

            mHandler.sendEmptyMessage(UPDATE_DATA);
        }
    }
    public void queryExam(final int year_code)
    {
        examList=onlineTestDB.loadExam(year_code);
        if(examList.size()>0)
        {
            Log.i(TAG, "queryExam:examList.size =  "+examList.size());
            displayExamList.clear();
            for(Exam exam1:examList)
            {
                String examContext=exam1.getExamContext();
                String first=exam1.getChoose_A();
                String second=exam1.getChoose_B();
                String third=exam1.getChoose_C();
                String fourth=exam1.getChoose_D();
                int examId=exam1.getId();
                Exam temp=new Exam(examContext,first,second,third,fourth,examId);

                displayExamList.add(temp);
            }

            mHandler.sendEmptyMessage(UPDATE_DATA);
        }
        else{
            Log.i(TAG, "queryExam: 数据查询为空");
            BmobQuery<Exam> exams=new BmobQuery<Exam>() ;
            exams.findObjects(this, new FindListener<Exam>() {
                @Override
                public void onSuccess(List<Exam> list) {


                    Log.i(TAG, "onSuccess: "+list.size());
                    for(Exam exam:list)
                    {
                        onlineTestDB.saveExam(exam);
                    }
                    test(year_code);
                    //因为存到数据库中后 希望从数据中重新读取数据 所以又调用了自己

                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(ExamShowActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            });




        }
    }

}


