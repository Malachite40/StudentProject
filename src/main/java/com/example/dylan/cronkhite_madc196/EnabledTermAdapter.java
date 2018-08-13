package com.example.dylan.cronkhite_madc196;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EnabledTermAdapter extends BaseAdapter {

    private Context mContext;
    private List<EnabledTerm> mEnabledTermList = new ArrayList<>();
    private ArrayList<View> listOfViews = new ArrayList<>();

    public EnabledTermAdapter(Context mContext, List<EnabledTerm> mEnabledTermList) {
        this.mContext = mContext;
        this.mEnabledTermList = mEnabledTermList;
    }
    @Override
    public int getCount() {
        return mEnabledTermList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEnabledTermList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.course_and_checkbox, null);
        TextView courseTitle = (TextView)v.findViewById(R.id.courseName);
        CheckBox checkBox = (CheckBox)v.findViewById(R.id.checkBox);

        courseTitle.setText(mEnabledTermList.get(position).courseName);

        if(mEnabledTermList.get(position).enabled){
            checkBox.toggle();
        }

        listOfViews.add(v);
        return v;
    }

    public void toggleCheckBox(int position){
        mEnabledTermList.get(position).enabled = !mEnabledTermList.get(position).enabled;
        CheckBox cb = (CheckBox)listOfViews.get(position).findViewById(R.id.checkBox);
        cb.toggle();
    }

    public List<EnabledTerm> getmEnabledTermList() {
        return mEnabledTermList;
    }
}
