package com.example.niyali.onlinetest.bean;

import java.util.Map;

/**
 * Created by niyali on 17/4/15.
 */

public class Message {
    private String title;
    private String message;
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String title, String message,String currentTime)
    {
        this.message=message;
        this.title=title;
        this.currentTime=currentTime;
    }

}
