package com.example.niyali.onlinetest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Explain;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by niyali on 17/4/28.
 */

public class ExplainAdapter extends ArrayAdapter<Explain> {
    private List<String> wrong;
    private int resourceId;
    private List<Explain> explainList;
    public ExplainAdapter(Context context, int resource, List<Explain> objects,List<String> wrongAnswer) {
        super(context, resource, objects);
        resourceId=resource;
        explainList = objects;
        wrong=wrongAnswer;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Explain explain= explainList.get(position);
        View view;
        ViewHolder viewHolder;
//        if(convertView==null)
//        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.correctAnswer=(TextView)view.findViewById(R.id.correct_answer);
            viewHolder.explain=(TextView)view.findViewById(R.id.explain);
            //view.setTag(viewHolder);

//        }else {
//            view=convertView;
//            viewHolder=(ViewHolder) view.getTag();
//        }

        if(wrong.contains(String.valueOf(explain.getId())))
        {
            viewHolder.correctAnswer.setTextColor(getContext().getResources().getColor(R.color.red));
            Log.i("ScoreActivity", "id: "+String.valueOf(explain.getId()));
        }
        viewHolder.correctAnswer.setText(String.valueOf(explain.getId())+"、 正确答案:"+explain.getCorrectAnswer());
       // viewHolder.correctAnswer.setTextColor(getContext().getResources().getColor(R.color.black));
        viewHolder.explain.setText(explain.getExplain());


        return view;

    }
    class ViewHolder{
        TextView correctAnswer;
        TextView explain;
    }
}
