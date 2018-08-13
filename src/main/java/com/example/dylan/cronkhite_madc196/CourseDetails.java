package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CourseDetails extends AppCompatActivity {

    TextView courseCodeTextView;
    TextView courseTitleTextView;
    TextView startDateTextField;
    TextView endDateTextView;
    TextView descriptionTextView;
    ListView mentorsListView;
    FloatingActionButton courseDetailEditFloatingAcitonButton;
    Toolbar toolbar;
    int courseId;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseCodeTextView = (TextView)findViewById(R.id.courseCodeTextView);
        courseTitleTextView = (TextView)findViewById(R.id.courseTitleTextView);
        startDateTextField = (TextView)findViewById(R.id.startDateTextField);
        endDateTextView = (TextView)findViewById(R.id.endDateTextView);
        descriptionTextView = (TextView)findViewById(R.id.descriptionTextView);
        mentorsListView = (ListView) findViewById(R.id.mentorsListView);
        courseDetailEditFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.courseDetailEditFloatingAcitonButton);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        courseId = getIntent().getIntExtra("courseId", -1);


        courseDetailEditFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditCourse.class);
                startIntent.putExtra("courseId", courseId);
                startActivity(startIntent);
            }
        });
        setCourseValues();
    }
    void setCourseValues(){
        Course c = db.getCourse(courseId);
        courseCodeTextView.setText("Course Code: "+c.getCourseCode());
        courseTitleTextView.setText("Course Name: "+c.getCourseName());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Timestamp sqlStartDate = Timestamp.valueOf(c.getStartDate());
        Timestamp sqlEndDate = Timestamp.valueOf(c.getEndDate());
        String startDate = sdf.format(sqlStartDate);
        String endDate = sdf.format(sqlEndDate);
        setStartDate(startDate);
        setEndDate(endDate);

        descriptionTextView.setText("    "+c.getCourseDescription());


    }
    void setStartDate(String s){
        startDateTextField.setText("Start Date: " + s);
    }
    void setEndDate(String s){
        endDateTextView.setText("End Date: " + s);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
