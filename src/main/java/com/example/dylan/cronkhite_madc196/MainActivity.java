package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
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
    Button termsButton, courseButton, mentorButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mentorButton = (Button)findViewById(R.id.mentorsButton);
        termsButton = (Button) findViewById(R.id.termsButton);
        courseButton = (Button)findViewById(R.id.coursesButton);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //term Button
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                startActivity(startIntent);
            }
        });
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CourseView.class);
                startActivity(startIntent);
            }
        });
        mentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MentorsView.class);
                startActivity(startIntent);
            }
        });

        java.sql.Timestamp sqlDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

        DatabaseHelper db = new DatabaseHelper(this);
//
        //tester
//        db.createMentorTable();
//        db.addMentor(new Mentor(1,"Dylan Cronkhite", "DylanEmail@gmail.com", "425-985-2107"));
//        db.printMentorTable();
//        db.updateMentor(new Mentor(1,"John Cronkhite", "DylanEmail@gmail.com", "425-985-2107"));
//        db.printMentorTable();


        //toast example
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
