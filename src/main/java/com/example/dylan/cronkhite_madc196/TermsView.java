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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.SyncFailedException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.support.v7.widget.Toolbar;

public class TermsView extends AppCompatActivity {
    ListView termsListView;
    ArrayList<String> termsList = new ArrayList<String>();
    LinkedHashMap<String, String> termAndDate = new LinkedHashMap<>();
    List<LinkedHashMap<String, String>> termAndDateList = new ArrayList<>();
    DatabaseHelper db = new DatabaseHelper(this);
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_view);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        //Variables
        termsListView = (ListView)findViewById(R.id.termsListView);
        FloatingActionButton addFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.addFloatingAcitonButton);


        addFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditTermView.class);
                startIntent.putExtra("test", "test");
                startActivity(startIntent);
            }
        });

        //test

        populateTermListView();

        termsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TermAdapter adapter = (TermAdapter)termsListView.getAdapter();
                Term t = (Term)adapter.getItem(position);
                Intent startIntent = new Intent(getApplicationContext(), TermMainView.class);
                startIntent.putExtra("termId", t.getTermID());
                startActivity(startIntent);
//                db.printTermTable();
//                Log.v("TERM ID SELECTED", Integer.toString(t.getTermID()));
            }
        });
    }

    void populateTermListView(){
        TermAdapter adapter = new TermAdapter(getApplicationContext(), db.getAllTerms());
        termsListView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_menu, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
