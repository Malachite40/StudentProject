package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button termsButton = (Button) findViewById(R.id.termsButton);

        //term Button
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                startIntent.putExtra("test", "test");
                startActivity(startIntent);
            }
        });

        java.sql.Timestamp sqlDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

        DatabaseHelper db = new DatabaseHelper(this);
        db.createAssesmentTable();
        db.addAssesment(new Assesment(1,"Title",sqlDate.toString(),1));
        db.printAssesmentTable();

//        db.updateCourse(new Course(1,sqlDate.toString(),sqlDate.toString(),1,"Name","Desc","C1000",false));
//        for(Course c : db.getAllCourses()){
//            Log.v("asdf: ", Integer.toString(c.getCourseID()));
//            Log.v("asdf: ", c.getStartDate());
//            Log.v("asdf: ", c.getEndDate());
//            Log.v("asdf: ", Integer.toString(c.getTermID()));
//            Log.v("asdf: ", c.getCourseName());
//            Log.v("asdf: ", c.getCourseDescription());
//            Log.v("asdf: ", c.getCourseCode());
//            Log.v("asdf: ", Boolean.toString(c.isAlertEnabled()));
//
//        }

//        boolean success = db.addTerm(sqlDate.toString(), sqlDate.toString(), "Term 1");

//        if(success){
//            Toast.makeText(MainActivity.this, "Data Successfully Added", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(MainActivity.this, "Data Failed To Added", Toast.LENGTH_LONG).show();
//        }


    }




}
