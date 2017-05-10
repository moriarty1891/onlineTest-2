package com.example.niyali.onlinetest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Record;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by niyali on 17/4/28.
 */

public class RecordAdapter extends ArrayAdapter<Record> {
    int resourceId;
    public RecordAdapter(Context context, int resource, List<Record> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder.examName=(TextView)view.findViewById(R.id.exam_name);
            viewHolder.time=(TextView)view.findViewById(R.id.time);
            viewHolder.score=(TextView)view.findViewById(R.id.score);
            viewHolder.wrongItem=(TextView)view.findViewById(R.id.wrong_item);
            view.setTag(viewHolder);

        }
        else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.examName.setText("试卷名: "+record.getTextName());
        viewHolder.time.setText(record.getTime());
        viewHolder.score.setText("得分:  "+String.valueOf(record.getScore()));
        viewHolder.wrongItem.setText("答错题目: " +record.getWrongItem());
        return view;

    }
    class ViewHolder{
        TextView examName;
        TextView time;
        TextView score;
        TextView wrongItem;
    }

}
