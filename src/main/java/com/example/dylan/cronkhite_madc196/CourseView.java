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
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CourseView extends AppCompatActivity {

    ListView coursesListView;
    DatabaseHelper db = new DatabaseHelper(this);
    FloatingActionButton addCourseFloatingActionButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        //variables
        coursesListView = (ListView)findViewById(R.id.courseListView);
        addCourseFloatingActionButton = (FloatingActionButton)findViewById(R.id.addCourseFloatingActionButton);

        final CourseListViewAdapter adapter = new CourseListViewAdapter(getApplicationContext(), db.getAllCourses());
        coursesListView.setAdapter(adapter);


        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startIntent = new Intent(getApplicationContext(), CourseDetails.class);
                Course c = (Course)adapter.getItem(position);
//                Log.v("TEST: ",c.getCourseName());
                startIntent.putExtra("courseId", c.getCourseID());
                startActivity(startIntent);
            }
        });
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
        inflater.inflate(R.menu.activity_course_menu, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
