package com.example.dylan.cronkhite_madc196;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseListAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<View> views = new ArrayList<>();
    ArrayList<Course> courses;

    public CourseListAdapter(Context mContext, ArrayList<Course> courses) {
        this.mContext = mContext;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.name_list_item, null);
        TextView text = (TextView) v.findViewById(R.id.text);

        try{
            text.setText(courses.get(position).getCourseName());
        }
        catch (Exception e){

            Log.v("Failed: ", "Course: "+courses.get(position).getCourseName());
        }

        views.add(v);
        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
