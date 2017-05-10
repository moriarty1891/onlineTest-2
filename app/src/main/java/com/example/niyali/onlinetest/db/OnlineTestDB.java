package com.example.niyali.onlinetest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import com.example.niyali.onlinetest.bean.Exam;
import com.example.niyali.onlinetest.bean.Explain;
import com.example.niyali.onlinetest.bean.Province;
import com.example.niyali.onlinetest.bean.Year;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niyali on 17/4/8.
 */

public class OnlineTestDB {
    public static final String DB_NAME="Online_Test";
    public static final int VERSION=1;
    private static OnlineTestDB onlineTestDB;
    private SQLiteDatabase db;
    private OnlineTestDB(Context context)
    {
        OnlineTestOpenHelper dbHelper=new OnlineTestOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static OnlineTestDB getInstance(Context context)
    {
        if(onlineTestDB==null)
        {
            onlineTestDB=new OnlineTestDB(context);
        }
        return onlineTestDB;
    }
    //将数据存储至数据库
    public void saveProvince(Province province)
    {
        if(province!=null)
        {
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceId());
            db.insert("Province",null,values);
        }
    }
    //从数据库中取数据
    public List<Province> loadProvince()
    {
        List<Province> list=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_code")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public void saveYear(Year year)
    {
        if(year!=null)
        {
            ContentValues values=new ContentValues();
            values.put("year_name",year.getYearName());
            values.put("year_code",year.getYearCode());
            values.put("province_code",year.getProvinceCode());
            db.insert("Year",null,values);
        }

    }
    public List<Year> loadYear(int province_code)
    {
        List<Year> years=new ArrayList<Year>();
        Cursor cursor=db.query("Year",null,"province_code= ?",new String[]{String.valueOf(province_code)}, null,null,null,null);

        if(cursor.moveToFirst())
        {
            do {
                Year year=new Year();
                year.setId(cursor.getInt(cursor.getColumnIndex("id")));
                year.setYearName(cursor.getString(cursor.getColumnIndex("year_name")));
                year.setYearCode(cursor.getInt(cursor.getColumnIndex("year_code")));
                year.setProvinceCode(province_code);
                years.add(year);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return years;
    }
    //exam_context text,choose_A text,choose_B text,choose_C text,choose_D text," +
    //"correct_answer text,year_code integer,explain text
    public void saveExam(Exam exam)
    {
        if(exam!=null)
        {
            ContentValues values=new ContentValues();
            values.put("exam_context",exam.getExamContext());
            values.put("choose_A",exam.getChoose_A());
            values.put("choose_B",exam.getChoose_B());
            values.put("choose_C",exam.getChoose_C());
            values.put("choose_D",exam.getChoose_D());
            values.put("correct_answer",exam.getCorrect_answer());

            values.put("year_code",exam.getYear_code());
            values.put("explain",exam.getExplain());
            values.put("id",exam.getId());
            db.insert("Exam",null,values);
        }
    }
    public List<Exam> loadExam(int year_code)
    {
        List<Exam> examList=new ArrayList<Exam>();
        Cursor cursor=db.query("Exam",null,"year_code=?",new String[]{String.valueOf(year_code)},null,null,"id");
        if(cursor.moveToFirst())
        {
            do {
                Exam exam = new Exam();
                exam.setId(cursor.getInt(cursor.getColumnIndex("id")));
                exam.setExamContext(cursor.getString(cursor.getColumnIndex("exam_context")));
                exam.setChoose_A(cursor.getString(cursor.getColumnIndex("choose_A")));
                exam.setChoose_B(cursor.getString(cursor.getColumnIndex("choose_B")));
                exam.setChoose_C(cursor.getString(cursor.getColumnIndex("choose_C")));
                exam.setChoose_D(cursor.getString(cursor.getColumnIndex("choose_D")));
                exam.setExplain(cursor.getString(cursor.getColumnIndex("explain")));
                exam.setCorrect_answer(cursor.getString(cursor.getColumnIndex("correct_answer")));
                exam.setYear_code(year_code);
                examList.add(exam);

            }while (cursor.moveToNext());

        }
        cursor.close();
        return examList;
    }
    public Map<String,String> queryCorrectAnswer(int year_code)
    {
        Map<String,String> allAnswer=new HashMap<String, String>();
        Cursor cursor=db.query("Exam",new String[]{"id","correct_answer"},"year_code=?",new String[]{String.valueOf(year_code)},null,null,"id");
        if(cursor.moveToFirst())
        {
            do {
                allAnswer.put(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))),cursor.getString(cursor.getColumnIndex("correct_answer")));
            }while (cursor.moveToNext());

        }
        cursor.close();
        return allAnswer;

    }
    public List<Explain> loadExplain(int year_code)
    {
        List<Explain> explains=new ArrayList<Explain>();
        Cursor cursor=db.query("Exam",new String[]{"id","correct_answer","explain"},"year_code=?",new String[]{String.valueOf(year_code)},null,null,"id");
        if(cursor.moveToFirst())
        {
            do {
                Explain explain=new Explain();

                explain.setCorrectAnswer(cursor.getString(cursor.getColumnIndex("correct_answer")));
                explain.setExplain(cursor.getString(cursor.getColumnIndex("explain")));
                explain.setId(cursor.getInt(cursor.getColumnIndex("id")));
                explains.add(explain);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return explains;

    }
}
