package com.example.niyali.onlinetest.loginabout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Province;
import com.example.niyali.onlinetest.bean.Year;
import com.example.niyali.onlinetest.db.OnlineTestDB;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by niyali on 17/4/3.
 */

public class ChooseAreaActivity extends AppCompatActivity {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_YEAR=1;
    private TextView titlebar;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private final List<String> dataList=new ArrayList<String>();
    private OnlineTestDB onlineTestDB;
    //省列表
    private List<Province> provinceList;
    //省对应的年份列表
    private List<Year> yearList;
    private int currentLevel;

    private Province selectedProvince;
    private Year selectedYear;
    private boolean isFromExamShowActivity;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        context=this;
        super.onCreate(savedInstanceState);
        isFromExamShowActivity=getIntent().getBooleanExtra("from_ExamShowActivity",false);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        titlebar=(TextView)findViewById(R.id.titlebar);
        listView=(ListView)findViewById(R.id.list_view);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        onlineTestDB=OnlineTestDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE)
                {
                    selectedProvince=provinceList.get(position);
                    queryYear();
                }else if(currentLevel==LEVEL_YEAR)
                {
                    selectedYear=yearList.get(position);
                    int yearCode=selectedYear.getYearCode();
                    Intent intent=new Intent(ChooseAreaActivity.this,ExamShowActivity.class);
                    intent.putExtra("from_activity",1);
                    SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("year_code",yearCode);
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvince();
        ExitApplication.getInstance().addActivity(this);

    }
    private void queryProvince()
    {
        provinceList=onlineTestDB.loadProvince();
        if(provinceList.size()>0)
        {
            dataList.clear();
            for(Province province:provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titlebar.setText("选择省份");
            currentLevel=LEVEL_PROVINCE;
        }
        else{
            BmobQuery<Province> provinces=new BmobQuery<Province>();
            provinces.findObjects(this,new FindListener<Province>(){
                @Override
                public void onSuccess(List<Province> list) {
//                    for(Province province:list)
//                    {
//                        dataList.add(province.getProvinceName());
//                    }
//                    adapter=new ArrayAdapter<String>(ChooseAreaActivity.this,android.R.push_layout.simple_list_item_1,dataList);
//                    listView.setAdapter(adapter);

                    for(Province province1 :list)
                    {
                        Province province=new Province();
                        province.setProvinceName(province1.getProvinceName());
                        province.setProvinceId(province1.getProvinceId());
                        onlineTestDB.saveProvince(province);
                    }
                    queryProvince();
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(ChooseAreaActivity.this,"查询失败",Toast.LENGTH_SHORT).show();

                }
            });
        }



    }
    private void queryYear()
    {
        yearList=onlineTestDB.loadYear(selectedProvince.getProvinceId());
        if(yearList.size()>0)
        {
            dataList.clear();
            for(Year year:yearList)
            {
                dataList.add(year.getYearName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titlebar.setText(selectedProvince.getProvinceName());
            currentLevel=LEVEL_YEAR;
        }
        else{
            BmobQuery<Year> years=new BmobQuery<Year>();
            //查询所有的年份信息
            years.findObjects(this, new FindListener<Year>() {
                @Override
                public void onSuccess(List<Year> list) {
                    for(Year year:list)
                    {
                        Year year1=new Year();
                        year1.setYearName(year.getYearName());
                        year1.setYearCode(year.getYearCode());
                        year1.setProvinceCode(year.getProvinceCode());
                        onlineTestDB.saveYear(year1);
                    }
                    queryYear();
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(ChooseAreaActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
