package com.example.dylan.cronkhite_madc196;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditTermView extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private ListView termListListView;
    ArrayList<EnabledTerm> enabledTermsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term_view);


        final TextView startDateTextView = (TextView) findViewById(R.id.startDateTextInput);
        final TextView endDateTextView = (TextView) findViewById(R.id.endDateTextInput);
        termListListView = (ListView)findViewById(R.id.termListListView);

        //start date
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTermView.this,
                        R.style.Theme_Design,
                        startDateSetListener,
                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDateTextView.setText("Start Date: " + dayOfMonth + "/" + month + "/" + year);
            }
        };
        //end date
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTermView.this,
                        R.style.Theme_Design,
                        endDateSetListener,
                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDateTextView.setText("End Date: " + dayOfMonth + "/" + month + "/" + year);
            }
        };

        populateTermListView();
    }
    void populateTermListView(){
        enabledTermsList.add(new EnabledTerm(true, "Course 1"));
        enabledTermsList.add(new EnabledTerm(true, "Course 2"));
        enabledTermsList.add(new EnabledTerm(false, "Course 3"));
        enabledTermsList.add(new EnabledTerm(false, "Course 4"));
        enabledTermsList.add(new EnabledTerm(true, "Course 5"));

        EnabledTermAdapter adapter = new EnabledTermAdapter(getApplicationContext(), enabledTermsList);

        termListListView.setAdapter(adapter);

        //getting info back
        //EnabledTerm enabled = (EnabledTerm)termListListView.getAdapter().getItem(0);
    }
}
