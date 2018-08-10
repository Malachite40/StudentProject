package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TermMainView extends AppCompatActivity {

    FloatingActionButton editFloatingAcitonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main_view);


        editFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.editFloatingAcitonButton);

        editFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditTermView.class);
                startIntent.putExtra("test", "test");
                startActivity(startIntent);
            }
        });
    }
}
