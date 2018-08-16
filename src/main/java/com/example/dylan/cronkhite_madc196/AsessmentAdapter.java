package com.example.dylan.cronkhite_madc196;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AsessmentAdapter extends BaseAdapter {
    ArrayList<Assesment> assesments = new ArrayList<>();
    ArrayList<View> views = new ArrayList<>();
    private Context mContext;
    DatabaseHelper db;

    @Override
    public int getCount() {
        return assesments.size();
    }

    @Override
    public Object getItem(int position) {
        return assesments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.assesment_list_item, null);
        TextView title = (TextView)v.findViewById((R.id.assesmentTitle));
        TextView courseNameTextView = (TextView)v.findViewById((R.id.courseNameTextView));
        TextView dueDateTextView = (TextView)v.findViewById((R.id.dueDateTextView));
        TextView alertTextView = (TextView)v.findViewById((R.id.alertTextView));

        Assesment a = assesments.get(position);
        try{
            Course c = db.getCourse(a.getCourseID());
            courseNameTextView.setText("Course: " + c.getCourseName());
        }
        catch(Exception e){
            courseNameTextView.setText("Course: Not Specified.");
        }

        title.setText(a.getAssesmentTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Timestamp dueDate = Timestamp.valueOf(a.getDueDate());
        String date = sdf.format(dueDate);

        dueDateTextView.setText(date);

        if(a.isAlertEnabled()){
            alertTextView.setText("Alert: Enabled");
        }
        else{
            alertTextView.setText("Alert: Disabled");
        }

        views.add(v);
        return v;
    }

    public AsessmentAdapter(ArrayList<Assesment> assesments, Context mContext, DatabaseHelper d) {
        this.assesments = assesments;
        this.mContext = mContext;
        this.db = d;
    }
}
