package com.example.dylan.cronkhite_madc196;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TermAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Term> terms = new ArrayList<>();
    ArrayList<View> views = new ArrayList<>();

    public TermAdapter(Context mContext, ArrayList<Term> terms) {
        this.mContext = mContext;
        this.terms = terms;
    }

    @Override
    public int getCount() {
        return terms.size();
    }

    @Override
    public Object getItem(int position) {
        return terms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.list_item_with_sub_item, null);
        TextView title = (TextView)v.findViewById(R.id.text1);
        TextView subText = (TextView)v.findViewById(R.id.text2);

        //set title
        title.setText(terms.get(position).getTermName());

        //set substring
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Timestamp sqlStartDate = Timestamp.valueOf(terms.get(position).getStartDate());
        Timestamp sqlEndDate = Timestamp.valueOf(terms.get(position).getEndDate());
        String startDate = sdf.format(sqlStartDate);
        String endDate = sdf.format(sqlEndDate);
        String startAndEndDateString = startDate + " - " + endDate;
        subText.setText(startAndEndDateString);

        views.add(v);
        return v;
    }

}
