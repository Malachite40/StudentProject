package com.example.dylan.cronkhite_madc196;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditTermView extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private ListView courseListListView;
    private DatabaseHelper db = new DatabaseHelper(this);
    ArrayList<EnabledTerm> enabledTermsList = new ArrayList<>();
    int termId = -1;
    TextInputEditText termNameTextInputLayout;
    java.sql.Timestamp startDate;
    java.sql.Timestamp endDate;
    TextView startDateTextView;
    TextView endDateTextView;
    Toolbar toolbar;

    String TERM_NULL_ERROR = "Term must have name";
    String START_DATE_AFTER_END_DATE_ERROR = "Start Date must be before End Date";
    String MUST_HAVE_START_AND_END_DATE_ERROR = "Must have Start and End Date";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term_view);


        startDateTextView = (TextView) findViewById(R.id.startDateTextInput);
        endDateTextView = (TextView) findViewById(R.id.endDateTextInput);
        courseListListView = (ListView)findViewById(R.id.courseListListView);
        FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.addFloatingAcitonButton);
        termNameTextInputLayout = (TextInputEditText)findViewById(R.id.termNameTextInputLayout);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        updateTermId();


        //start date
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTermView.this,
                        R.style.Theme_Design,
                        startDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDateTextView.setText("Start Date: " + dayOfMonth + "/" + month + "/" + year);
                updateStartDate(year,month,dayOfMonth);
            }
        };
        //end date
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTermView.this,
                        R.style.Theme_Design,
                        endDateSetListener,
                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDateTextView.setText("End Date: " + dayOfMonth + "/" + month + "/" + year);
                updateEndDate(year,month,dayOfMonth);
            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTerm();
            }
        });


        courseListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnabledTermAdapter adapter = (EnabledTermAdapter)courseListListView.getAdapter();
                adapter.toggleCheckBox(position);
            }
        });
        populateTermListView();
        updateData();
    }
    void updateData(){
        if(termId > 0){
            Log.v("Term:",Integer.toString(termId));
            Term t = db.getTerm(termId);

            termNameTextInputLayout.setText(t.getTermName());
            updateStartDate(Timestamp.valueOf(t.getStartDate()));
            updateEndDate(Timestamp.valueOf(t.getEndDate()));

        }
    }
    void updateEndDate(Timestamp t){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(t);
        endDateTextView.setText("End Date: " + date);
        setEndDate(t);
    }
    void updateStartDate(Timestamp t){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(t);
        startDateTextView.setText("Start Date: " + date);
        setStartDate(t);
    }
    void updateEndDate(int year, int month, int dayOfMonth){
        String addZeroMonth = "";
        String addZeroDay = "";
        if(month < 10){
            addZeroMonth = "0";
        }
        if(dayOfMonth < 10){
            addZeroDay = "0";
        }

        String string = year + "-" + addZeroMonth + month + "-" + addZeroDay + dayOfMonth + " 00:00:00.00";
        setEndDate(Timestamp.valueOf(string));
    }
    void updateStartDate(int year, int month, int dayOfMonth){
        String addZeroMonth = "";
        String addZeroDay = "";
        if(month < 10){
            addZeroMonth = "0";
        }
        if(dayOfMonth < 10){
            addZeroDay = "0";
        }

        String string = year + "-" + addZeroMonth + month + "-" + addZeroDay + dayOfMonth + " 00:00:00.00";
        setStartDate(Timestamp.valueOf(string));
    }
    void updateTermId(){
        try{
            termId = getIntent().getIntExtra("termId", -1);
            Log.v("ID: ", Integer.toString(termId));
        }
        catch (Exception e){
            Log.v("ID: ", "FAILED TO GET ID");

        }
    }
    void populateTermListView(){
        for(Course c : db.getAllCourses()){
            boolean enabled = isTermRelatedToCourse(termId, c.getCourseID());

            enabledTermsList.add(new EnabledTerm(c.getCourseID(),enabled,c.getCourseName()));
        }

        EnabledTermAdapter adapter = new EnabledTermAdapter(getApplicationContext(), enabledTermsList);

        courseListListView.setAdapter(adapter);


    }
    boolean isTermRelatedToCourse(int termId, int courseId){
        return db.isTermRelatedToCourse(termId,courseId);
    }
    void addTerm(){
        if(checkInputs()){
            Log.v("Term ID: ", Integer.toString(termId));
            if(termId > 0){
                String termName = termNameTextInputLayout.getText().toString();

                //add term
                DatabaseHelper db = new DatabaseHelper(this);
                db.updateTerm(new Term(termId,startDate.toString(),endDate.toString(),termName));

                updateTermCourseSet();

                Intent startIntent = new Intent(getApplicationContext(), TermMainView.class);
                startIntent.putExtra("termId", termId);
                startActivity(startIntent);
            }
            else{
                String termName = termNameTextInputLayout.getText().toString();

                //add term
                DatabaseHelper db = new DatabaseHelper(this);
                db.addTerm(new Term(1,startDate.toString(),endDate.toString(),termName));

                updateTermCourseSet();

                Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                startActivity(startIntent);
            }
        }
    }
    boolean checkInputs(){

        String termName = termNameTextInputLayout.getText().toString();
        if(termName.isEmpty()){
            Toast.makeText(EditTermView.this, TERM_NULL_ERROR, Toast.LENGTH_LONG).show();
            return false;
        }
        try{
            if(startDate.after(endDate)){
                Toast.makeText(EditTermView.this, START_DATE_AFTER_END_DATE_ERROR, Toast.LENGTH_LONG).show();
                return false;
            }
        }
        catch(Exception e){
            Toast.makeText(EditTermView.this, MUST_HAVE_START_AND_END_DATE_ERROR, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void updateTermCourseSet(){
        deleteExistingEnabledCourses();
        createEnabledCourses();
    }
    public  void deleteExistingEnabledCourses(){
        DatabaseHelper db = new DatabaseHelper(this);
        EnabledTermAdapter adapter = (EnabledTermAdapter)courseListListView.getAdapter();
        for (EnabledTerm t : adapter.getmEnabledTermList()){
            db.deleteTermCourseSet(termId, t.courseId);
        }
    }
    public void createEnabledCourses(){
        DatabaseHelper db = new DatabaseHelper(this);
        EnabledTermAdapter adapter = (EnabledTermAdapter)courseListListView.getAdapter();
        for (EnabledTerm t : adapter.getmEnabledTermList()){
            if(t.enabled){
                if(termId == -1){
                    db.addTermCourse(new TermCourseSet(1,db.getNextTermID()-1,t.courseId));
                }
                else{
                    db.addTermCourse( new TermCourseSet(1,termId,t.courseId));
                }
            }
        }
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    void deleteTerm(){
        db.deleteTerm(termId);
        Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
        startActivity(startIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.v("HJERE","HERE");
                Intent startIntent = new Intent(getApplicationContext(), TermsView.class);
                startActivity(startIntent);
                return super.onOptionsItemSelected(item);

            case R.id.save_button:
                addTerm();
                return super.onOptionsItemSelected(item);

            case R.id.delete_button:
                deleteTerm();
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_edit_term_view, menu);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}
