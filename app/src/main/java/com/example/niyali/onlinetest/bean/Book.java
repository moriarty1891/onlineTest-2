package com.example.niyali.onlinetest.bean;

/**
 * Created by niyali on 17/4/15.
 */

public class Book {
    private String bookName;
    private int imageId;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public Book(String bookName,int imageId)
    {
        this.bookName=bookName;
        this.imageId=imageId;
    }
}
