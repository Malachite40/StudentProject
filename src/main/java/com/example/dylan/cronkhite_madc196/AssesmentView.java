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

import java.util.Date;

public class AssesmentView extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseHelper db = new DatabaseHelper(this);
    ListView assesmentListView;
    FloatingActionButton addFloatingAcitonButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_view);

        addFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.addFloatingAcitonButton);
        assesmentListView = (ListView)findViewById(R.id.assesmentListView);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);


        populateListView();

        addFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditAssesmentView.class);
                startActivity(startIntent);
            }
        });
        assesmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AsessmentAdapter adapter = (AsessmentAdapter) assesmentListView.getAdapter();
                Intent startIntent = new Intent(getApplicationContext(), EditAssesmentView.class);
                Assesment a = (Assesment)adapter.getItem(position);
                startIntent.putExtra("assessmentId", a.getAssesmentID());
                startActivity(startIntent);
            }
        });
    }
    void populateListView(){

        AsessmentAdapter adapter = new AsessmentAdapter(db.getAllAssesments(), getApplicationContext(), db);

        assesmentListView.setAdapter(adapter);
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
