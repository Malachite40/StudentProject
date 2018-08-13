package com.example.dylan.cronkhite_madc196;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MentorListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Mentor> mentors = new ArrayList<>();
    private ArrayList<View> views = new ArrayList<>();

    public MentorListAdapter(Context mContext, ArrayList<Mentor> mentors) {
        this.mContext = mContext;
        this.mentors = mentors;
    }

    @Override
    public int getCount() {
        return mentors.size();
    }

    @Override
    public Object getItem(int position) {
        return mentors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(mContext, R.layout.mentor_list_item, null);
        TextView name=(TextView)v.findViewById(R.id.nameTextView);
        TextView phone = (TextView)v.findViewById(R.id.phoneTextView);
        TextView email = (TextView)v.findViewById(R.id.emailTextView);

        name.setText(mentors.get(position).getMentorName());
        phone.setText(mentors.get(position).getPhone());
        email.setText(mentors.get(position).getEmail());

        views.add(v);
        return v;
    }
}
