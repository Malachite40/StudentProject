package com.example.dylan.cronkhite_madc196;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "school.db";
    SQLiteDatabase database;
    //term
    public static final String TERM_TABLE_NAME = "term";
    public static final String TERM_ID = "termId";
    public static final String TERM_START_DATE = "termStartDate";
    public static final String TERM_END_DATE = "termEndDate";
    public static final String TERM_NAME = "termName ";

    //course
    public static final String COURSE_TABLE_NAME = "course";
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_START_DATE = "courseStartDate";
    public static final String COURSE_END_DATE = "courseEndDate";
    //term ID
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_DESCRIPTION = "courseDescription";
    public static final String COURSE_CODE = "courseCode";
    public static final String COURSE_ALERT = "courseAlert";

    //assesment
    public static final String ASSESMENT_TABLE_NAME = "assesment";
    public static final String ASSESMENT_ID = "assesmentId";
    public static final String ASSESMENT_TITLE = "assesmentTitle";
    public static final String ASSESMENT_DUE_DATE = "assesmentDueDate";
    //course ID



    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        createTermTable();
        createCourseTable();
        createAssesmentTable();
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        database.execSQL("DROP IF TABLE EXISTS " + TERM_TABLE_NAME);
        onCreate(db);
    }
    public boolean checkForTableExists(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+table+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
    //term
    public void createTermTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + TERM_TABLE_NAME + " (" +
                TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERM_START_DATE + " TEXT, " +
                TERM_END_DATE + " TEXT, " +
                TERM_NAME + " TEXT)";

        database.execSQL(createTable);
    }
    public boolean addTerm(Term t){
//        java.sql.Timestamp sqlDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TERM_START_DATE, t.getStartDate());
        contentValues.put(TERM_END_DATE, t.getEndDate());
        contentValues.put(TERM_NAME, t.getTermName());

        long result = db.insert(TERM_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }

    }
    public void printTermTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TERM_TABLE_NAME,null);
        StringBuffer buffer = new StringBuffer();
        while(data.moveToNext()){
            buffer.append("\n");
            buffer.append(TERM_ID + ": " + data.getString(0) + "\n");
            buffer.append(TERM_START_DATE + ": " + data.getString(1) + "\n");
            buffer.append(TERM_END_DATE + ": " + data.getString(2) + "\n");
            buffer.append(TERM_NAME + ": " + data.getString(3) + "\n");

        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<Term> getAllTerms(){
        ArrayList<Term> terms = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TERM_TABLE_NAME,null);
        while(data.moveToNext()){
            terms.add(new Term(Integer.parseInt(data.getString(0)), data.getString(1), data.getString(2), data.getString(3)));
        }
        return terms;
    }
    public boolean updateTerm(Term t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TERM_START_DATE, t.getStartDate());
        contentValues.put(TERM_END_DATE, t.getEndDate());
        contentValues.put(TERM_NAME, t.getTermName());

        String id = Integer.toString(t.getTermID());

        db.update(TERM_TABLE_NAME, contentValues, TERM_ID + "  = ?", new String[]{id});
        return true;
    }

    //courses
    public void createCourseTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + COURSE_TABLE_NAME + " (" +
                COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSE_START_DATE + " TEXT, " +
                COURSE_END_DATE + " TEXT, " +
                TERM_ID + " INTEGER, " +
                COURSE_NAME + " TEXT, " +
                COURSE_DESCRIPTION + " TEXT, " +
                COURSE_CODE + " TEXT, " +
                COURSE_ALERT + " INTEGER, " +
                " FOREIGN KEY("+TERM_ID+") REFERENCES "+TERM_TABLE_NAME+"("+TERM_ID+"))";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addCourse(Course c){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_START_DATE, c.getStartDate());
        contentValues.put(COURSE_END_DATE, c.getEndDate());
        contentValues.put(TERM_ID, c.getTermID());
        contentValues.put(COURSE_NAME, c.getCourseName());
        contentValues.put(COURSE_DESCRIPTION, c.getCourseDescription());
        contentValues.put(COURSE_CODE, c.getCourseCode());
        if(c.isAlertEnabled()){
            contentValues.put(COURSE_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_ALERT, 0);
        }


        long result = db.insert(COURSE_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printCourseTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + COURSE_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(COURSE_ID + ": " + data.getString(0) + "\n");
            buffer.append(COURSE_START_DATE + ": " + data.getString(1) + "\n");
            buffer.append(COURSE_END_DATE + ": " + data.getString(2) + "\n");
            buffer.append(TERM_ID + ": " + data.getString(3) + "\n");
            buffer.append(COURSE_NAME + ": " + data.getString(4) + "\n");
            buffer.append(COURSE_DESCRIPTION + ": " + data.getString(5) + "\n");
            buffer.append(COURSE_CODE + ": " + data.getString(6) + "\n");
            buffer.append(COURSE_ALERT + ": " + data.getString(7) + "\n");

        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> course = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + COURSE_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            int termid = Integer.parseInt(data.getString(3));

            String s = data.getString(7);
            int bool = Integer.parseInt(s);
            boolean alertEnabled = false;
            if(bool == 1){
                alertEnabled = true;
            }

            course.add(new Course(id,
                    data.getString(1),
                    data.getString(2),
                    termid, data.getString(4),
                    data.getString(5),
                    data.getString(6),
                    alertEnabled));
        }
        return course;
    }
    public boolean updateCourse(Course c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_START_DATE, c.getStartDate());
        contentValues.put(COURSE_END_DATE, c.getEndDate());
        contentValues.put(TERM_ID, c.getTermID());
        contentValues.put(COURSE_NAME, c.getCourseName());
        contentValues.put(COURSE_DESCRIPTION, c.getCourseDescription());
        contentValues.put(COURSE_CODE, c.getCourseCode());
        if(c.isAlertEnabled()){
            contentValues.put(COURSE_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_ALERT, 0);
        }


        String id = Integer.toString(c.getCourseID());
        db.update(COURSE_TABLE_NAME, contentValues, COURSE_ID + "  = ?", new String[]{id});
        return true;
    }

    //assesments
    public void createAssesmentTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + ASSESMENT_TABLE_NAME + " (" +
                ASSESMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ASSESMENT_TITLE + " TEXT, " +
                ASSESMENT_DUE_DATE + " TEXT, " +
                COURSE_ID + " INTEGER, " +
                " FOREIGN KEY("+COURSE_ID+") REFERENCES "+COURSE_TABLE_NAME+"("+COURSE_ID+"))";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addAssesment(Assesment a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSESMENT_TITLE, a.getAssesmentTitle());
        contentValues.put(ASSESMENT_DUE_DATE, a.getDueDate());
        contentValues.put(COURSE_ID, a.getCourseID());

        long result = db.insert(ASSESMENT_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printAssesmentTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ASSESMENT_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(ASSESMENT_ID + ": " + data.getString(0) + "\n");
            buffer.append(ASSESMENT_TITLE+ ": " + data.getString(1) + "\n");
            buffer.append(ASSESMENT_DUE_DATE + ": " + data.getString(2) + "\n");
            buffer.append(COURSE_ID + ": " + data.getString(3) + "\n");
        }
        Log.v("----------------------", buffer.toString());
    }

    //CHECK THESE TWO
    public ArrayList<Assesment> getAllAssesments(){
        ArrayList<Assesment> assesments = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + COURSE_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            int courseid = Integer.parseInt(data.getString(3));
            assesments.add(new Assesment(id,
                    data.getString(1),
                    data.getString(2),
                    courseid));
        }
        return assesments;
    }
    public boolean updateAssesment(Assesment a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSESMENT_TITLE, a.getAssesmentTitle());
        contentValues.put(ASSESMENT_DUE_DATE, a.getDueDate());
        contentValues.put(COURSE_ID, a.getCourseID());

        String id = Integer.toString(a.getAssesmentID());
        db.update(ASSESMENT_TABLE_NAME, contentValues, ASSESMENT_ID + "  = ?", new String[]{id});
        return true;
    }
}
