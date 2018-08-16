package com.example.dylan.cronkhite_madc196;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button termsButton, courseButton, mentorButton, assesmentButton;
    Toolbar toolbar;

    final String PLEASE_MAKE_MENTOR_FIRST = "You must make a mentor first.";
    String PLEASE_CREATE_TERM_NEXT = "You must create a term first.";
    String PLEASE_CREATE_COURSE_NEXT = "You must create a course first.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mentorButton = (Button)findViewById(R.id.mentorsButton);
        termsButton = (Button) findViewById(R.id.termsButton);
        courseButton = (Button)findViewById(R.id.coursesButton);
        assesmentButton = (Button)findViewById(R.id.assesmentButton);

        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        final DatabaseHelper db = new DatabaseHelper(this);
        db.createAllTables();

        //term Button
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getNextMentorId() != 1){
                    Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                    startActivity(startIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, PLEASE_MAKE_MENTOR_FIRST, Toast.LENGTH_SHORT).show();
                }
            }
        });
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getNextTermID() != 1){
                    Intent startIntent = new Intent(getApplicationContext(), CourseView.class);
                    startActivity(startIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, PLEASE_CREATE_TERM_NEXT, Toast.LENGTH_SHORT).show();
                }

            }
        });
        mentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MentorsView.class);
                startActivity(startIntent);
            }
        });
        assesmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getNextCourseID() != 1){
                    Intent startIntent = new Intent(getApplicationContext(), AssesmentView.class);
                    startActivity(startIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, PLEASE_CREATE_COURSE_NEXT, Toast.LENGTH_SHORT).show();
                }
            }
        });

        java.sql.Timestamp sqlDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

//            Toast.makeText(MainActivity.this, "Data Successfully Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
