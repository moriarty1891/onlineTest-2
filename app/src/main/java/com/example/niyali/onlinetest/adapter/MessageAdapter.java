package com.example.niyali.onlinetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Message;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by niyali on 17/4/15.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;

    public MessageAdapter(Context context, int resourceId,List<Message> objects) {
        super(context, resourceId,objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Message message=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView pushTitle=(TextView)view.findViewById(R.id.push_title);
        TextView pushMessage=(TextView)view.findViewById(R.id.push_message);
        TextView currentTime=(TextView)view.findViewById(R.id.current_time);
        currentTime.setText(message.getCurrentTime());
        pushTitle.setText(message.getTitle());
        pushMessage.setText(message.getMessage());
        return view;
    }

}
