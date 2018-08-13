package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TermMainView extends AppCompatActivity {

    FloatingActionButton editFloatingAcitonButton;
    int termId = -1;
    TextView termName;
    TextView startDate;
    TextView endDate;
    ListView courseListView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main_view);

        //variables
        termName = (TextView)findViewById(R.id.termNameTextView);
        startDate = (TextView)findViewById(R.id.startDateTextView);
        endDate = (TextView)findViewById(R.id.endDateTextView);
        courseListView = (ListView)findViewById(R.id.coursesListView);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        try{
            termId = getIntent().getIntExtra("termId", 0);
        }
        catch(Exception e){
            e.printStackTrace();
            Log.v("Failed: ", "FAILED TO GET TERM ID");
        }


        editFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.editFloatingAcitonButton);

        editFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditTermView.class);
                startIntent.putExtra("termId", termId);
                startActivity(startIntent);
            }
        });

        //set term info
        DatabaseHelper db = new DatabaseHelper(this);
        Term t = db.getTerm(termId);
        setTermName(t.getTermName());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Timestamp sqlStartDate = Timestamp.valueOf(t.getStartDate());
        Timestamp sqlEndDate = Timestamp.valueOf(t.getEndDate());
        String startDate = sdf.format(sqlStartDate);
        String endDate = sdf.format(sqlEndDate);
        setStartDate(startDate);
        setEndDate(endDate);

        setCourses(db.getAllCoursesForTerm(termId));

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    void setTermName(String s){
        termName.setText(s);
    }
    void setStartDate(String s){
        startDate.setText("Start Date: " + s);
    }
    void setEndDate(String s){
        endDate.setText("End Date: " + s);
    }
    void setCourses(ArrayList<Course> courses){
        CourseListAdapter adapter = new CourseListAdapter(getApplicationContext(), courses);
        courseListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                startActivity(startIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_main_view_menu, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
