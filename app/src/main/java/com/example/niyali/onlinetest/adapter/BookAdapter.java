package com.example.niyali.onlinetest.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Book;

import java.util.List;

/**
 * Created by niyali on 17/4/15.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private int resourceId;
    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book=getItem(position);
        View view;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else {
            view=convertView;
        }

        ImageView bookImage=(ImageView)view.findViewById(R.id.image_book);
        TextView bookName=(TextView)view.findViewById(R.id.book_name);
        bookImage.setImageResource(book.getImageId());
        bookName.setText(book.getBookName());
        return view;
    }


}
