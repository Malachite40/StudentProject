package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CourseDetails extends AppCompatActivity {

    TextView courseCodeTextView;
    TextView courseTitleTextView;
    TextView startDateTextField;
    TextView endDateTextView;
    TextView descriptionTextView;
    ListView mentorsListView;
    FloatingActionButton courseDetailEditFloatingAcitonButton;

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


        courseDetailEditFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditCourse.class);
                startIntent.putExtra("test", "test");
                startActivity(startIntent);
            }
        });
    }
}
