package com.example.dylan.cronkhite_madc196;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
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


public class EditCourse extends AppCompatActivity {
    Toolbar toolbar;
    int courseId;
    ListView mentorListView, termListView;
    TextInputEditText courseCodeTextInput, statusInputText, courseNameTextInput, descriptionTextInput;
    CheckBox startDateAlert, endDateAlert;
    TextView startDateTextView, endDateTextView;

    String courseName, courseCode, status, description;
    Timestamp startDate, endDate;
    boolean startDateAlertEnabled, endDateAlertEnabled;
    int termId;

    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;

    DatabaseHelper db = new DatabaseHelper(this);

    String CORUSE_MUST_HAVE_NAME = "Course must have a Title.";
    String COURSE_MUST_HAVE_STATUS = "Course must have Status.";
    String COURSE_MUST_HAVE_COURSE_CODE = "Course must have Course Code.";
    String COURSE_MUST_HAVE_TERM = "Course must have Term.";
    String COURSE_MUST_HAVE_START_DATE = "Course must have Start Date.";
    String COURSE_MUST_HAVE_END_DATE = "Course must have End Date.";
    String COURSE_START_DATE_MUST_BE_BEFORE_END = "Course start date must be before end date.";
    String COURSE_MUST_HAVE_DESCRIPTION = "Course must have Description.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        toolbar = (Toolbar)findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        courseId = getIntent().getIntExtra("courseId", -1);

        mentorListView = (ListView)findViewById(R.id.mentorListView);
        termListView = (ListView)findViewById(R.id.termsListView);

        courseCodeTextInput = (TextInputEditText)findViewById(R.id.courseCodeTextInput);
        statusInputText = (TextInputEditText)findViewById(R.id.statusInputText);
        courseNameTextInput = (TextInputEditText)findViewById(R.id.courseNameTextInput);
        descriptionTextInput = (TextInputEditText)findViewById(R.id.descriptionTextInput);

        startDateAlert = (CheckBox)findViewById(R.id.startDateAlert);
        endDateAlert = (CheckBox)findViewById(R.id.endDateAlert);

        startDateTextView = (TextView)findViewById(R.id.startDateTextView);
        endDateTextView = (TextView)findViewById(R.id.endDateTextView);


        //start date
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this,
                        R.style.Theme_Design,
                        startDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setStartDate(dayOfMonth + "/" + month + "/" + year);
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

                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this,
                        R.style.Theme_Design,
                        endDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setEndDate(dayOfMonth + "/" + month + "/" + year);
                updateEndDate(year,month,dayOfMonth);
            }
        };

        termListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnabledTermAdapter adapter = (EnabledTermAdapter) termListView.getAdapter();
                adapter.setAllCheckBoxes(false);
                adapter.toggleCheckBox(position);
            }
        });

        mentorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnabledTermAdapter adapter = (EnabledTermAdapter)mentorListView.getAdapter();
                adapter.toggleCheckBox(position);
            }
        });

        populateFields();
        populateTermList();
        populateMentorList();
    }
    void populateFields(){
        if(courseId > 0){
            Course c = db.getCourse(courseId);

            courseCodeTextInput.setText(c.getCourseCode());
            descriptionTextInput.setText(c.getCourseDescription());
            statusInputText.setText(c.getStatus());
            courseNameTextInput.setText(c.getCourseName());
            startDate = Timestamp.valueOf(c.getStartDate());
            endDate = Timestamp.valueOf(c.getEndDate());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            setStartDate(sdf.format(startDate));
            setEndDate(sdf.format(endDate));
            startDateAlert.setChecked(c.isAlertStartEnabled());
            endDateAlert.setChecked(c.isAlertEndEnabled());


        }
    }
    void populateTermList(){
        ArrayList<EnabledTerm> enabledTerms = new ArrayList<>();
        for (Term t : db.getAllTerms()){
            boolean enabled = false;
            try{
                Course c = db.getCourse(courseId);
//                Log.v("XXXXXX:::: ", "COURSE TERM ID: " + Integer.toString(c.getTermID()) + " TERM ID: " + Integer.toString(t.getTermID()));

                if(t.getTermID() == c.getTermID()){
                    enabled = true;
                }
            }
            catch(Exception e){

            }
            enabledTerms.add(new EnabledTerm(t.getTermID(),enabled,t.getTermName()));
        }

        EnabledTermAdapter adapter = new EnabledTermAdapter(getApplicationContext(), enabledTerms);
        termListView.setAdapter(adapter);
    }
    void populateMentorList(){
        ArrayList<EnabledTerm> enabledTerms = new ArrayList<>();

        for (Mentor m : db.getAllMentors()){
            boolean enabled = false;
            try{
                for (CourseMentorSet set : db.getAllCourseMentorSets()){
                    if(m.getMentorID() == set.getMentorID() && set.getCourseID() == courseId){
                        enabled = true;
                    }
                }
            }
            catch(Exception e){

            }
            enabledTerms.add(new EnabledTerm(m.getMentorID(),enabled,m.getMentorName()));
        }

        EnabledTermAdapter adapter = new EnabledTermAdapter(getApplicationContext(), enabledTerms);
        mentorListView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startIntent = new Intent(getApplicationContext(), CourseDetails.class);
                startIntent.putExtra("courseId", courseId);
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

            if(courseId > 0){
                db.updateCourse(new Course(courseId,
                        startDate.toString(),
                        endDate.toString(),
                        termId,
                        courseName,
                        description,
                        courseCode,
                        startDateAlertEnabled,
                        endDateAlertEnabled,
                        status));
            }
            else{
                db.addCourse(new Course(courseId,
                        startDate.toString(),
                        endDate.toString(),
                        termId,
                        courseName,
                        description,
                        courseCode,
                        startDateAlertEnabled,
                        endDateAlertEnabled,
                        status));
            }
            updateCourseMentorSets();
            updateNotifications();
            Intent startIntent = new Intent(getApplicationContext(), CourseView.class);
            startActivity(startIntent);
        }
    }
    void deleteButton(){
        if(courseId > 0){
            db.deleteCourseMentorSets(courseId);
            db.deleteCourse(courseId);
            Intent startIntent = new Intent(getApplicationContext(), CourseView.class);
            startActivity(startIntent);
        }
    }
    void updateCourseMentorSets(){
        deleteAllRelatedCourseMentorSets();
        createCourseMentorSets();
    }
    void createCourseMentorSets(){
        EnabledTermAdapter adapter = (EnabledTermAdapter)mentorListView.getAdapter();
        if(courseId > 0){

        }
        else{
            courseId = db.getNextCourseID()-1;
        }
        for (EnabledTerm t : adapter.getmEnabledTermList()){
            if(t.enabled){
                db.addCourseMentor(new CourseMentorSet(1,t.courseId,courseId));
            }
        }
    }
    void deleteAllRelatedCourseMentorSets(){
        db.deleteCourseMentorSets(courseId);
    }
    void updateInputs(){
        try{
            courseCode = courseCodeTextInput.getText().toString();
            status = statusInputText.getText().toString();
            courseName = courseNameTextInput.getText().toString();
            description = descriptionTextInput.getText().toString();

            //TERM UPDATE
            EnabledTermAdapter adapter = (EnabledTermAdapter)termListView.getAdapter();
            for (EnabledTerm t : adapter.getmEnabledTermList()){
                if(t.enabled){
                    termId = t.courseId;
                }
            }

            if(startDateAlert.isChecked()){
                startDateAlertEnabled = true;
            }
            else{
                startDateAlertEnabled = false;
            }
            if(endDateAlert.isChecked()){
                endDateAlertEnabled = true;
            }
            else{
                endDateAlertEnabled = false;
            }
        }
        catch(Exception e){

        }
    }
    boolean checkInputs(){
        updateInputs();

        if(courseName.isEmpty()){
            Toast.makeText(EditCourse.this, CORUSE_MUST_HAVE_NAME, Toast.LENGTH_LONG).show();
            return false;
        }
        if(courseCode.isEmpty()){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_COURSE_CODE, Toast.LENGTH_LONG).show();
            return false;
        }
        if(status.isEmpty()){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_STATUS, Toast.LENGTH_LONG).show();
            return false;
        }
        if(startDate == null){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_START_DATE, Toast.LENGTH_LONG).show();
            return false;
        }
        if(endDate == null){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_END_DATE, Toast.LENGTH_LONG).show();
            return false;
        }
        if(endDate.before(startDate)){
            Toast.makeText(EditCourse.this, COURSE_START_DATE_MUST_BE_BEFORE_END, Toast.LENGTH_LONG).show();
            return false;
        }
        EnabledTermAdapter adapter = (EnabledTermAdapter)termListView.getAdapter();
        if(!adapter.atLeastOneChecked()){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_TERM, Toast.LENGTH_LONG).show();
            return false;
        }
        if(description.isEmpty()){
            Toast.makeText(EditCourse.this, COURSE_MUST_HAVE_DESCRIPTION, Toast.LENGTH_LONG).show();
            return false;
        }
        //check term
        return true;
    }
    void setStartDate(String s){
        startDateTextView.setText("Start Date: " + s);
    }
    void setEndDate(String s){
        endDateTextView.setText("End Date: " + s);
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
        endDate = Timestamp.valueOf(string);
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
        startDate = Timestamp.valueOf(string);
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
