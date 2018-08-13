package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EditCourse extends AppCompatActivity {
    Toolbar toolbar;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        courseId = getIntent().getIntExtra("courseId", -1);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), MentorsView.class);
                startActivity(startIntent);
                return super.onOptionsItemSelected(item);
            case R.id.save_button:
                saveButton();
                return super.onOptionsItemSelected(item);
            case R.id.delete_button:
                deleteButton();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_edit_term_view, menu);

        return super.onCreateOptionsMenu(menu);
    }
    void saveButton(){

    }
    void deleteButton(){

    }
}
