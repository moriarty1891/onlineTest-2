package com.example.niyali.onlinetest.bean;

import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;

import cn.bmob.v3.BmobObject;

/**
 * Created by niyali on 17/4/8.
 */

public class Exam extends BmobObject{
    private String examContext;
    private String choose_A;
    private String choose_B;
    private String choose_C;
    private String choose_D;
    private int year_code;
    private int id;
    private String explain;
    private String correct_answer;

    public String getExamContext() {
        return examContext;
    }

    public void setExamContext(String examContext) {
        this.examContext = examContext;
    }

    public String getChoose_A() {
        return choose_A;
    }

    public void setChoose_A(String choose_A) {
        this.choose_A = choose_A;
    }

    public String getChoose_B() {
        return choose_B;
    }

    public void setChoose_B(String choose_B) {
        this.choose_B = choose_B;
    }

    public String getChoose_C() {
        return choose_C;
    }

    public void setChoose_C(String choose_C) {
        this.choose_C = choose_C;
    }

    public String getChoose_D() {
        return choose_D;
    }

    public void setChoose_D(String choose_D) {
        this.choose_D = choose_D;
    }

    public int getYear_code() {
        return year_code;
    }

    public void setYear_code(int year_code) {
        this.year_code = year_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public Exam(String examContext, String first, String second, String third, String fourth,int examId)
    {
        this.examContext=examContext;
        this.choose_A=first;
        this.choose_B=second;
        this.choose_C=third;
        this.choose_D=fourth;
        this.id=examId;
    }
    public Exam()
    {

    }
}
