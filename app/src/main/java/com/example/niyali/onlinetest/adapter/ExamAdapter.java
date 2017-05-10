package com.example.niyali.onlinetest.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.bean.Exam;

import java.util.List;

/**
 * Created by niyali on 17/4/9.
 */

public class ExamAdapter extends ArrayAdapter<Exam> {

    private int resourceId;

    public ExamAdapter(Context context, int textViewResourceId,List<Exam> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent)
    {
        final Exam exam=getItem(position);
        View view;
        if (convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        else{
            view=convertView;
        }
        TextView examContext=(TextView) view.findViewById(R.id.each_context);
        RadioGroup radioGroup=(RadioGroup) view.findViewById(R.id.radiogroup);
        RadioButton first=(RadioButton) view.findViewById(R.id.first);
        RadioButton second=(RadioButton) view.findViewById(R.id.second);
        RadioButton third=(RadioButton) view.findViewById(R.id.third);
        RadioButton fourth=(RadioButton) view.findViewById(R.id.fourth);
        examContext.setText(String.valueOf(exam.getId())+"、 "+exam.getExamContext());
        first.setText(exam.getChoose_A());
        second.setText(exam.getChoose_B());
        third.setText(exam.getChoose_C());
        fourth.setText(exam.getChoose_D());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor;
                editor=pref.edit();
                String examId=String.valueOf(exam.getId());
                switch (checkedId)
                {
                    case R.id.first:
                        editor.putString(examId,"A");
                        break;
                    case R.id.second:
                        editor.putString(examId,"B");
                        break;
                    case R.id.third:
                        editor.putString(examId,"C");
                        break;
                    case R.id.fourth:
                        editor.putString(examId,"D");
                        break;
                    default:

                        break;

                }
                editor.commit();
            }
        });

        return view;

    }
}
