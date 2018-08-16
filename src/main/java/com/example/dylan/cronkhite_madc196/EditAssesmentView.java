package com.example.dylan.cronkhite_madc196;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditAssesmentView extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener dueDateListener;
    TextInputEditText assessmentTitleTextView;
    CheckBox alertCheckBox;
    TextView dueDateTextView;
    ListView courseListView;

    String assesmentTitle;
    boolean alertEnabled;
    Timestamp dueDate;
    int courseId = -1;

    DatabaseHelper db = new DatabaseHelper(this);
    int assessmentId;
    Toolbar toolbar;
    FloatingActionButton addFloatingAcitonButton;


    String INVALID_INPUTS = "Something went wrong.";
    String EMPTY_TITLE = "Assessment needs Title.";
    String EMPTY_DUE_DATE = "Please select a Due Date.";
    String EMPTY_COURSE_ID  = "Select a corresponding course";
    String CANNOT_DELETE_NEW_ASSESSMENT = "Can't delete a new assessment.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assesment_view);

        assessmentTitleTextView = (TextInputEditText)findViewById(R.id.assessmentTitleTextView);
        alertCheckBox = (CheckBox)findViewById(R.id.alertCheckBox);
        dueDateTextView = (TextView)findViewById(R.id.dueDateTextView);
        courseListView = (ListView)findViewById(R.id.courseListView);
        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        addFloatingAcitonButton = (FloatingActionButton)findViewById(R.id.addFloatingAcitonButton);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        populateListView();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnabledTermAdapter adapter = (EnabledTermAdapter) courseListView.getAdapter();
                adapter.setAllCheckBoxes(false);
                adapter.toggleCheckBox(position);
            }
        });
        addFloatingAcitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
        dueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditAssesmentView.this,
                        R.style.Theme_Design,
                        dueDateListener,
                        year,month,day);
                dialog.show();
            }
        });
        dueDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dueDateTextView.setText("Start Date: " + dayOfMonth + "/" + month + "/" + year);
                updateStartDate(year,month,dayOfMonth);
            }
        };
        updateAssesmentInfo();
    }
    void updateAssesmentInfo(){
        if(assessmentId > 0){
            Assesment a = db.getAssesment(assessmentId);
            assessmentTitleTextView.setText(a.getAssesmentTitle());
            if(a.isAlertEnabled()){
                alertCheckBox.toggle();
            }

            updateDueDate(Timestamp.valueOf(a.getDueDate()));

            EnabledTermAdapter adapter = (EnabledTermAdapter) courseListView.getAdapter();
            adapter.enableCheckBoxByCourseId(courseId);
        }
    }
    void updateDueDate(Timestamp t){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(t);
        dueDateTextView.setText("Start Date: " + date);
        setDueDate(t);
    }
    void populateListView(){
        ArrayList<EnabledTerm> enabledTermsList = new ArrayList<>();

        for(Course c : db.getAllCourses()){
            boolean enabled = false;
            if(assessmentId > 0){
                Assesment a = db.getAssesment(assessmentId);
                if(c.getCourseID() == a.getCourseID()){
                    enabled = true;
                }
            }

            enabledTermsList.add(new EnabledTerm(c.getCourseID(),enabled,c.getCourseName()));
        }
         EnabledTermAdapter adapter = new EnabledTermAdapter(getApplicationContext(), enabledTermsList);
        courseListView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), AssesmentView.class);
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

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
    void saveButton(){
        if(checkInputs()){
            if(assessmentId > 0){
                db.updateAssesment(new Assesment(assessmentId,assesmentTitle,dueDate.toString(),courseId,alertEnabled));
            }
            else{
                db.addAssesment(new Assesment(assessmentId,assesmentTitle,dueDate.toString(),courseId,alertEnabled));
            }
            updateNotifications();
            Intent startIntent = new Intent(getApplicationContext(), AssesmentView.class);
            startActivity(startIntent);
        }
    }
    void deleteButton(){
        if(assessmentId > 0){
            db.deleteAssesment(assessmentId);
            Intent startIntent = new Intent(getApplicationContext(), AssesmentView.class);
            startActivity(startIntent);
        }
        else{
            Toast.makeText(EditAssesmentView.this, CANNOT_DELETE_NEW_ASSESSMENT, Toast.LENGTH_LONG).show();
        }

    }
    boolean checkInputs(){
        updateInputs();

        if(assesmentTitle.isEmpty()){
            Toast.makeText(EditAssesmentView.this, EMPTY_TITLE, Toast.LENGTH_LONG).show();
            return false;
        }
        if(dueDate == null){
            Toast.makeText(EditAssesmentView.this, EMPTY_DUE_DATE, Toast.LENGTH_LONG).show();
            return false;
        }
        if(courseId == -1){
            Toast.makeText(EditAssesmentView.this, EMPTY_COURSE_ID, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    void updateInputs(){
        try{
            assesmentTitle = assessmentTitleTextView.getText().toString();

            if(alertCheckBox.isChecked()){
                alertEnabled = true;
            }
            else{
                alertEnabled = false;
            }

            EnabledTermAdapter adapter = (EnabledTermAdapter) courseListView.getAdapter();
            for (EnabledTerm e : adapter.getmEnabledTermList()){
                if(e.enabled){
                    courseId = e.courseId;
                }
            }
        }
        catch(Exception e){
            Toast.makeText(EditAssesmentView.this, INVALID_INPUTS, Toast.LENGTH_LONG).show();
        }
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
        setDueDate(Timestamp.valueOf(string));
    }
    public void setDueDate(Timestamp d) {
        this.dueDate = d;
    }
    void updateNotifications(){
        deleteAllNotifications();
        createAllNotifications();
    }
    void deleteAllNotifications(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        for (Notification n : db.getAllNotifications()){
            nm.cancel(n.getNotificationId());
        }
        db.deleteAllNotifications();
    }
    void createAllNotifications(){

        for(Course c : db.getAllCourses()){
            if(c.isAlertStartEnabled()){
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
                notification.setAutoCancel(true);

                notification.setSmallIcon(R.drawable.ic_launcher_background);

                Timestamp ts = Timestamp.valueOf(c.getStartDate());
                notification.setWhen(ts.getTime());

                notification.setContentTitle("Alert: " + c.getCourseName());
                notification.setContentText("Start of course Alert.");

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pIntent);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(db.getNextNotificationId(), notification.build());

                db.addNotification(new Notification(1,c.getCourseName(),"Sub Text",ts));
            }
            if(c.isAlertEndEnabled()){
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
                notification.setAutoCancel(true);

                notification.setSmallIcon(R.drawable.ic_launcher_background);

                Timestamp ts = Timestamp.valueOf(c.getEndDate());
                notification.setWhen(ts.getTime());

                notification.setContentTitle("Alert: " + c.getCourseName());
                notification.setContentText("End of course Alert.");

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pIntent);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(db.getNextNotificationId(), notification.build());

                db.addNotification(new Notification(1,c.getCourseName(),"Sub Text",ts));
            }
        }
        for(Assesment a : db.getAllAssesments()){
            if(a.isAlertEnabled()){
                if(a.isAlertEnabled()){
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
                    notification.setAutoCancel(true);

                    notification.setSmallIcon(R.drawable.ic_launcher_background);

                    Timestamp ts = Timestamp.valueOf(a.getDueDate());
                    notification.setWhen(ts.getTime());

                    notification.setContentTitle("Alert: " + a.getAssesmentTitle());
                    notification.setContentText("Due date Alert for Assessment.");

                    Intent intent = new Intent(this, MainActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pIntent);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(db.getNextNotificationId(), notification.build());

                    db.addNotification(new Notification(1,a.getAssesmentTitle(),"Sub Text",ts));
                }
            }
        }

        db.printAllNotificaitons();
    }
}

