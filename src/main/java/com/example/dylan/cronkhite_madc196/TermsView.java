package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TermsView extends AppCompatActivity {
    ListView termsListView;
    ArrayList<String> termsList = new ArrayList<String>();
    LinkedHashMap<String, String> termAndDate = new LinkedHashMap<>();
    List<LinkedHashMap<String, String>> termAndDateList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_view);

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
                Intent startIntent = new Intent(getApplicationContext(), TermMainView.class);
                startIntent.putExtra("test", "test");
                startActivity(startIntent);
            }
        });
    }

    void populateTermListView(){
        termAndDate.put("Term 1", "Date 1");
        termAndDate.put("Term 2", "Date 2");
        termAndDate.put("Term 3", "Date 3");
        termAndDate.put("Term 4", "Date 4");

        SimpleAdapter adapter = new SimpleAdapter(this, termAndDateList, R.layout.list_item_with_sub_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});

        Iterator it = termAndDate.entrySet().iterator();
        while(it.hasNext()){
            LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            termAndDateList.add(resultsMap);
        }
        termsListView.setAdapter(adapter);
    }
}
