package com.example.niyali.onlinetest.bean;

import android.content.Context;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by niyali on 17/4/30.
 */

public class MyBmobInstallation extends BmobInstallation {


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    public MyBmobInstallation(Context context) {
        super(context);
    }
}
