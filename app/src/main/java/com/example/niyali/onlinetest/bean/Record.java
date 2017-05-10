package com.example.niyali.onlinetest.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by niyali on 17/4/26.
 */

public class Record extends BmobObject{
    private int id;
    private String username;
    private int yearCode;
    private String textName;
    private int score;
    private String wrongItem;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYearCode() {
        return yearCode;
    }

    public void setYearCode(int yearCode) {
        this.yearCode = yearCode;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getWrongItem() {
        return wrongItem;
    }

    public void setWrongItem(String wrongItem) {
        this.wrongItem = wrongItem;
    }
}
