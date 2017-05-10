package com.example.niyali.onlinetest.loginabout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.ExplainAdapter;
import com.example.niyali.onlinetest.bean.Explain;
import com.example.niyali.onlinetest.bean.Record;
import com.example.niyali.onlinetest.db.OnlineTestDB;
import com.example.niyali.onlinetest.util.Helper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by niyali on 17/4/26.
 */

public class ScoreActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private TextView scoreText;
    private Map<String,?> score;
    String regex="\\d+";
    //试题个数
    private int textSize;
    private TextView allScore;
    private OnlineTestDB onlineTestDB;
    private ListView listView;
    public static final String TAG="ScoreActivity";
    private List<Explain> explainList;
    int yearCode;
    int num;
    private Context context;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score_show);
        onlineTestDB=OnlineTestDB.getInstance(this);
        allScore=(TextView)findViewById(R.id.all_score);
        scoreText=(TextView)findViewById(R.id.score_text);
        listView=(ListView)findViewById(R.id.result_listview);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        score=pref.getAll();
        yearCode=pref.getInt("year_code",0);


        Pattern pattern=Pattern.compile(regex);
        Matcher matcher;
        Map<String,String> answer=new HashMap<String, String>();
        //全部题号
        List<String> allId=new ArrayList<String>();
        //记录用户正确的题号
        List<String> userCorrectId=new ArrayList<String>();
        //记录用户错误的题号
        List<String> userWrongId=new ArrayList<String>();
        //用户答案:
        for(Map.Entry<String,?> entry:score.entrySet())
        {
            String key=entry.getKey();
            matcher=pattern.matcher(key);
            if(matcher.matches())
            {
                answer.put(key,(String) entry.getValue());
            }
        }
        //从数据库中查询正确的答案信息
        Map<String,String> correctAnswer=onlineTestDB.queryCorrectAnswer(yearCode);
        textSize=correctAnswer.size();
        allScore.setText("满分: "+ String.valueOf(textSize*10));
        Iterator<String> iter=correctAnswer.keySet().iterator();
        while (iter.hasNext())
        {

            allId.add(iter.next());
        }
        if (correctAnswer.size()>0)
        {
            num=0;
            Iterator<String> iterator=answer.keySet().iterator();
            while (iterator.hasNext())
            {
                String answerKey=(String)iterator.next();
                if(answer.get(answerKey).equals(correctAnswer.get(answerKey))){
                    userCorrectId.add(answerKey);
                    num++;
                }
            }
            scoreText.setText(String.valueOf(num*10));

        }
        Log.i(TAG, "answer: "+answer.toString());
        Log.i(TAG, "correct: "+correctAnswer.toString());
         for(String str:allId)
         {
             if (!userCorrectId.contains(str))
             {
                 userWrongId.add(str);
             }
         }
        Log.i(TAG, "wrong: "+userWrongId.toString());

        explainList=onlineTestDB.loadExplain(yearCode);
        ExplainAdapter adapter=new ExplainAdapter(ScoreActivity.this,R.layout.each_explain,explainList,userWrongId);
        listView.setAdapter(adapter);

        saveToServer(userWrongId);
        ExitApplication.getInstance().addActivity(this);
    }
    public String findTextName(int yearCode)
    {
        int count;
        int areaId;
        int year;
        count=Helper.sizeOfInt(yearCode);
        if (count==2)
        {
            return "北京地区 "+"20"+String.valueOf(yearCode);
        }
        if (count==3)
        {
            areaId=yearCode/100;
            year=yearCode%100;
            if(areaId==1)
            {
                return "上海地区 "+"20"+String.valueOf(year);
            }
            if(areaId==2)
            {
                return "河南地区 "+"20"+String.valueOf(year);
            }
        }
        return "Wrong";
    }
    public void saveToServer(List<String> userWrongId)
    {
        StringBuilder builder=new StringBuilder();
        for(String str :userWrongId)
        {
            builder.append(str+",");
        }
        Date dt=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        String WrongItem=builder.toString();
        int length=WrongItem.length();
        Record record=new Record();
        record.setTextName(findTextName(yearCode));
        record.setYearCode(yearCode);
        record.setUsername(pref.getString("username","lili"));
        record.setScore(num*10);
        record.setWrongItem(builder.toString().substring(0,length-1));
        record.setTime(sdf.format(dt));
        record.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"保存数据",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }
}
