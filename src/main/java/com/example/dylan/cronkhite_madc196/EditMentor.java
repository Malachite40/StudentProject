package com.example.dylan.cronkhite_madc196;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class EditMentor extends AppCompatActivity {
    Toolbar toolbar;
    int mentorId;
    DatabaseHelper db = new DatabaseHelper(this);
    TextInputEditText mentorName, mentorPhone, mentorEmail;
    FloatingActionButton addFloatingAcitonButton;
    String name;
    String phone;
    String email;
    String CANT_DELETE_NEW_MENTOR = "Can't delete mentor that hasn't been created.";
    String ALL_FIELDS_MUST_BE_FILLED = "All fields must be filled out.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);

        //variables
        addFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.addFloatingAcitonButton);
        mentorName = (TextInputEditText)findViewById(R.id.nameInputField);
        mentorPhone = (TextInputEditText)findViewById(R.id.phoneInputField);
        mentorEmail = (TextInputEditText)findViewById(R.id.emailInputField);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        mentorId = getIntent().getIntExtra("mentorId", -1);
        populateFields();

        addFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
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
    void populateFields(){
        if(mentorId > 0){
            Mentor m = db.getMentor(mentorId);

            mentorName.setText(m.getMentorName());
            mentorEmail.setText(m.getEmail());
            mentorPhone.setText(m.getPhone());

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_edit_term_view, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
    void deleteButton(){
        if(mentorId>0){
            db.deleteMentor(mentorId);
            Intent startIntent = new Intent(getApplicationContext(), MentorsView.class);
            startActivity(startIntent);
        }
        else{
            Toast.makeText(EditMentor.this, CANT_DELETE_NEW_MENTOR, Toast.LENGTH_LONG).show();
        }
    }
    void saveButton(){
        if(checkInputs()){
            if(mentorId > 0){
                db.updateMentor(new Mentor(mentorId, name,email,phone));
            }
            else{
                db.addMentor(new Mentor(1, name,email,phone));
            }
            Intent startIntent = new Intent(getApplicationContext(), MentorsView.class);
            startActivity(startIntent);
        }
    }
    void fetchInputs(){
        name = mentorName.getText().toString();
        phone = mentorPhone.getText().toString();
        email = mentorEmail.getText().toString();
    }
    boolean checkInputs(){
        fetchInputs();
        if(name.isEmpty() || phone.isEmpty() || email.isEmpty()){
            Toast.makeText(EditMentor.this, ALL_FIELDS_MUST_BE_FILLED, Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }
}
