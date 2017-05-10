package com.example.niyali.onlinetest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by niyali on 17/4/8.
 */

public class OnlineTestOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_PROVINCE="create table Province(id integer primary key" +
            " autoincrement,province_name text,province_code integer)";
    public static final String CREATE_YEAR="create table Year(id integer primary key" +
            " autoincrement,year_name text,year_code integer,province_code integer)";
    public static final String CREATE_EXAM="create table Exam(id integer" +
            " ,exam_context text,choose_A text,choose_B text,choose_C text,choose_D text," +
            "correct_answer text,year_code integer,explain text)";

    public OnlineTestOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                int version)
    {
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_YEAR);
        db.execSQL(CREATE_EXAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
