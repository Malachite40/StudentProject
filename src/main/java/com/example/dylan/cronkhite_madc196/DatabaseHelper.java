package com.example.dylan.cronkhite_madc196;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "school.db";
    public static DatabaseHelper dbhelper;
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
    public static final String COURSE_START_ALERT = "courseStartAlert";
    public static final String COURSE_END_ALERT = "courseEndAlert";
    public static final String COURSE_STATUS = "courseStatus";

    //assesment
    public static final String ASSESMENT_TABLE_NAME = "assesment";
    public static final String ASSESMENT_ID = "assesmentId";
    public static final String ASSESMENT_TITLE = "assesmentTitle";
    public static final String ASSESMENT_DUE_DATE = "assesmentDueDate";
    public static final String ASSESMENT_ALERT = "assesmentAlert";
    //course ID

    //mentor
    public static final String MENTOR_TABLE_NAME = "mentor";
    public static final String MENTOR_ID = "mentorId";
    public static final String MENTOR_NAME = "mentorName";
    public static final String MENTOR_EMAIL = "mentorEmail";
    public static final String MENTOR_PHONE = "mentorPhone";

    //course - mentor set
    public static final String COURSEMENTOR_TABLE_NAME = "courseMentorSet";
    public static final String COURSEMENTOR_ID = "courseMentorSetId";
    //mentor ID
    //course ID

    //term - course set
    public static final String TERMCOURSE_TABLE_NAME = "termCourseSet";
    public static final String TERMCOURSE_ID = "termCourseId";
    //term id
    //course id

    //notification
    public static final String NOTIFICATION_TABLE_NAME = "notificationTable";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_TITLE = "notificationTitle";
    public static final String NOTIFICATION_SUB_TEXT = "notificationSubText";
    public static final String NOTIFICATION_TIME = "notificationTime";
    @Override
    public void onCreate(SQLiteDatabase db) {
//        database = this.getWritableDatabase();
        if(dbhelper == null){
            dbhelper = this;
        }
        database = db;
        createAllTables();
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
    public void deleteAllData(){
        deleteAllTerms();
        deleteAllCourses();
        deleteAllAssesments();
        deleteAllMentors();
        deleteAllCourseMentorSets();
        deleteAllTermCourseSet();
    }

    public void createAllTables(){
        createTermTable();
        createTermCourseTable();
        createCourseTable();
        createAssesmentTable();
        createCourseMentorTable();
        createMentorTable();
        createNotificationTable();
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
    public int getNextTermID(){
        int max = 0;
        for(Term t : getAllTerms()){
            if(t.getTermID()>max){
                max = t.getTermID();
            }
        }
        return max+1;
    }
    public Term getTerm(int id){
        for(Term t : getAllTerms()){
            if(t.getTermID() == id){
                return  t;
            }
        }
        return null;
    }
    public void deleteAllTerms(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TERM_TABLE_NAME, null,null);
    }
    public void deleteTerm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sid = Integer.toString(id);
        db.delete(TERM_TABLE_NAME, TERM_ID + " = ?",new String[]{sid});
    }

    //courses
    public void createCourseTable(){
//        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME);
        String createTable = "CREATE TABLE IF NOT EXISTS " + COURSE_TABLE_NAME + " (" +
                COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSE_START_DATE + " TEXT, " +
                COURSE_END_DATE + " TEXT, " +
                TERM_ID + " INTEGER, " +
                COURSE_NAME + " TEXT, " +
                COURSE_DESCRIPTION + " TEXT, " +
                COURSE_CODE + " TEXT, " +
                COURSE_START_ALERT + " INTEGER, " +
                COURSE_END_ALERT + " INTEGER, " +
                COURSE_STATUS + " INTEGER, " +
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
        contentValues.put(COURSE_STATUS, c.getStatus());
        if(c.isAlertStartEnabled()){
            contentValues.put(COURSE_START_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_START_ALERT, 0);
        }
        if(c.isAlertEndEnabled()){
            contentValues.put(COURSE_END_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_END_ALERT, 0);
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
            buffer.append(COURSE_START_ALERT + ": " + data.getString(7) + "\n");
            buffer.append(COURSE_END_ALERT + ": " + data.getString(8) + "\n");
            buffer.append(COURSE_STATUS + ": " + data.getString(9) + "\n");
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
            boolean alertStartEnabled = false;
            if(bool == 1){
                alertStartEnabled = true;
            }
            String b = data.getString(8);
            int end = Integer.parseInt(b);
            boolean alertEndEnabled = false;
            if(end == 1){
                alertEndEnabled = true;
            }
            String status = data.getString(9);
            course.add(new Course(id,
                    data.getString(1),
                    data.getString(2),
                    termid, data.getString(4),
                    data.getString(5),
                    data.getString(6),
                    alertStartEnabled,
                    alertEndEnabled,
                    status));
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
        contentValues.put(COURSE_STATUS, c.getStatus());
        if(c.isAlertStartEnabled()){
            contentValues.put(COURSE_START_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_START_ALERT, 0);
        }
        if(c.isAlertEndEnabled()){
            contentValues.put(COURSE_END_ALERT, 1);
        }
        else{
            contentValues.put(COURSE_END_ALERT, 0);
        }

        String id = Integer.toString(c.getCourseID());
        db.update(COURSE_TABLE_NAME, contentValues, COURSE_ID + "  = ?", new String[]{id});
        return true;
    }
    public Course getCourse(int id){
        for (Course c : getAllCourses()){
            if(c.getCourseID() == id){
                return  c;
            }
        }
        return null;
    }
    public void deleteAllCourses(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSE_TABLE_NAME, null,null);
    }
    public int getNextCourseID(){
        int max = 0;
        for(Course t : getAllCourses()){
            if(t.getCourseID()>max){
                max = t.getCourseID();
            }
        }
        return max+1;
    }
    public void deleteCourse(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sid = Integer.toString(id);
        db.delete(COURSE_TABLE_NAME, COURSE_ID + " = ?",new String[]{sid});
    }


    //assesments
    public void createAssesmentTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + ASSESMENT_TABLE_NAME + " (" +
                ASSESMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ASSESMENT_TITLE + " TEXT, " +
                ASSESMENT_DUE_DATE + " TEXT, " +
                COURSE_ID + " INTEGER, " +
                ASSESMENT_ALERT + " INTEGER, " +
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
        int s = 0;
        if(a.isAlertEnabled()){
            s = 1;
        }
        contentValues.put(ASSESMENT_ALERT,s);

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
            buffer.append(ASSESMENT_ALERT + ": " + data.getString(4));
        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<Assesment> getAllAssesments(){
        ArrayList<Assesment> assesments = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ASSESMENT_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            int courseid = Integer.parseInt(data.getString(3));
            boolean alert = false;
            if(Integer.parseInt(data.getString(4)) == 1){
                alert = true;
            }
            assesments.add(new Assesment(id,
                    data.getString(1),
                    data.getString(2),
                    courseid,
                    alert));
        }
        return assesments;
    }
    public boolean updateAssesment(Assesment a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSESMENT_TITLE, a.getAssesmentTitle());
        contentValues.put(ASSESMENT_DUE_DATE, a.getDueDate());
        contentValues.put(COURSE_ID, a.getCourseID());
        String s = "0";
        if(a.isAlertEnabled()){
            s = "1";
        }
        contentValues.put(ASSESMENT_ALERT,s);

        String id = Integer.toString(a.getAssesmentID());
        db.update(ASSESMENT_TABLE_NAME, contentValues, ASSESMENT_ID + "  = ?", new String[]{id});
        return true;
    }
    public Assesment getAssesment(int id){
        for (Assesment a : getAllAssesments()){
            if(a.getAssesmentID() == id){
                return a;
            }
        }
        return null;
    }
    public void deleteAssesment(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sid = Integer.toString(id);
        db.delete(ASSESMENT_TABLE_NAME, ASSESMENT_ID + " = ?",new String[]{sid});
    }
    public void deleteAllAssesments(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSESMENT_TABLE_NAME, null,null);
    }

    //menotr
    public void createMentorTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + MENTOR_TABLE_NAME + " (" +
                MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MENTOR_NAME + " TEXT, " +
                MENTOR_EMAIL + " TEXT, " +
                MENTOR_PHONE + " TEXT)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addMentor(Mentor m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENTOR_NAME, m.getMentorName());
        contentValues.put(MENTOR_EMAIL, m.getEmail());
        contentValues.put(MENTOR_PHONE, m.getPhone());

        long result = db.insert(MENTOR_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printMentorTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + MENTOR_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(MENTOR_ID + ": " + data.getString(0) + "\n");
            buffer.append(MENTOR_NAME + ": " + data.getString(1) + "\n");
            buffer.append(MENTOR_EMAIL + ": " + data.getString(2) + "\n");
            buffer.append(MENTOR_PHONE + ": " + data.getString(3) + "\n");
        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<Mentor> getAllMentors(){
        ArrayList<Mentor> mentor = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + MENTOR_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            mentor.add(new Mentor(id,
                    data.getString(1),
                    data.getString(2),
                    data.getString(3)));
        }
        return mentor;
    }
    public boolean updateMentor(Mentor m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENTOR_NAME, m.getMentorName());
        contentValues.put(MENTOR_EMAIL, m.getEmail());
        contentValues.put(MENTOR_PHONE, m.getPhone());

        String id = Integer.toString(m.getMentorID());
        db.update(MENTOR_TABLE_NAME, contentValues, MENTOR_ID + "  = ?", new String[]{id});
        return true;
    }
    public Mentor getMentor(int id){
        for(Mentor m : getAllMentors()){
            if(m.getMentorID() == id){
                return m;
            }
        }
        return null;
    }
    public void deleteMentor(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sid = Integer.toString(id);
        db.delete(MENTOR_TABLE_NAME, MENTOR_ID + " = ?",new String[]{sid});
    }
    public void deleteAllMentors(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MENTOR_TABLE_NAME, null,null);
    }
    public int getNextMentorId(){
        int max = 0;
        for(Mentor m : getAllMentors()){
            if(m.getMentorID()>max){
                max = m.getMentorID();
            }
        }
        return max+1;
    }

    //course-mentor set
    public void createCourseMentorTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + COURSEMENTOR_TABLE_NAME + " (" +
                COURSEMENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MENTOR_ID + " INTEGER, " +
                COURSE_ID + " INTEGER, " +
                " FOREIGN KEY("+MENTOR_ID+") REFERENCES "+MENTOR_TABLE_NAME+"("+MENTOR_ID+"), " +
                " FOREIGN KEY("+COURSE_ID+") REFERENCES "+COURSE_TABLE_NAME+"("+COURSE_ID+"))";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addCourseMentor(CourseMentorSet cms){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENTOR_ID, cms.getMentorID());
        contentValues.put(COURSE_ID, cms.getCourseID());

        long result = db.insert(COURSEMENTOR_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printCourseMentorTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + COURSEMENTOR_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(COURSEMENTOR_ID + ": " + data.getString(0) + "\n");
            buffer.append(MENTOR_ID + ": " + data.getString(1) + "\n");
            buffer.append(COURSE_ID + ": " + data.getString(2) + "\n");
        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<CourseMentorSet> getAllCourseMentorSets(){
        ArrayList<CourseMentorSet> cms = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + COURSEMENTOR_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            int mentorId = Integer.parseInt(data.getString(1));
            int courseId = Integer.parseInt(data.getString(2));
            cms.add(new CourseMentorSet(id,mentorId,courseId));
        }
        return cms;
    }
    public boolean updateCourseMentorSet(CourseMentorSet cms){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENTOR_ID, cms.getMentorID());
        contentValues.put(COURSE_ID, cms.getCourseID());

        String id = Integer.toString(cms.getCourseMentorSetID());
        db.update(COURSEMENTOR_TABLE_NAME, contentValues, COURSEMENTOR_ID + "  = ?", new String[]{id});
        return true;
    }
    public void deleteAllCourseMentorSets(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSEMENTOR_TABLE_NAME, null,null);
    }
    public void deleteCourseMentorSets(int courseId){
        String cid = Integer.toString(courseId);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(COURSEMENTOR_TABLE_NAME, COURSE_ID + " = ?", new String[]{cid});
        if(result == -1){
            Log.v("FAILED TO DELETE", "");
        }
    }

    //term - course set
    public void createTermCourseTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + TERMCOURSE_TABLE_NAME + " (" +
                TERMCOURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERM_ID + " INTEGER, " +
                COURSE_ID + " INTEGER, " +
                " FOREIGN KEY("+TERM_ID+") REFERENCES "+TERM_TABLE_NAME+"("+TERM_ID+"), " +
                " FOREIGN KEY("+COURSE_ID+") REFERENCES "+COURSE_TABLE_NAME+"("+COURSE_ID+"))";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addTermCourse(TermCourseSet tcs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TERM_ID, tcs.getTermId());
        contentValues.put(COURSE_ID, tcs.getCourseId());

        long result = db.insert(TERMCOURSE_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printTermCourseTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TERMCOURSE_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(TERMCOURSE_ID + ": " + data.getString(0) + "\n");
            buffer.append(TERM_ID + ": " + data.getString(1) + "\n");
            buffer.append(COURSE_ID + ": " + data.getString(2) + "\n");
        }
        Log.v("----------------------", buffer.toString());
    }
    public ArrayList<TermCourseSet> getAllTermCourseSets(){
        ArrayList<TermCourseSet> tcs = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TERMCOURSE_TABLE_NAME,null);
        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            int termId = Integer.parseInt(data.getString(1));
            int courseId = Integer.parseInt(data.getString(2));
            tcs.add(new TermCourseSet(id,termId,courseId));
        }
        return tcs;
    }
    public boolean updateTermCourseSet(TermCourseSet tcs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TERM_ID, tcs.getTermId());
        contentValues.put(COURSE_ID, tcs.getCourseId());

        String id = Integer.toString(tcs.getTermCourseId());
        db.update(TERMCOURSE_TABLE_NAME, contentValues, TERMCOURSE_ID + "  = ?", new String[]{id});
        return true;
    }
    public void deleteTermCourseSet(int termId, int courseId){
        String termID = Integer.toString(termId);
        String courseID = Integer.toString(courseId);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TERMCOURSE_TABLE_NAME, TERM_ID + " = ? AND " + COURSE_ID + " = ?", new String[] {termID, courseID});
        Log.v("Attempted to delete: ", TERM_ID + " = "+Integer.toString(termId) + " AND " + COURSE_ID + " = " + Integer.toString(courseId));
        if(result == -1){
            Log.v("FAILED TO DELETE", "");
        }
    }
    public ArrayList<Course> getAllCoursesForTerm(int termId){
        ArrayList<Course> returnList = new ArrayList<>();
        for (TermCourseSet set : getAllTermCourseSets()){
            if(set.getTermId() == termId){
                returnList.add(getCourse(set.getCourseId()));
            }
        }
        return returnList;
    }
    public void deleteAllTermCourseSet(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TERMCOURSE_TABLE_NAME,null,null);
    }
    public boolean isTermRelatedToCourse(int termId, int courseId){
        for (TermCourseSet s : getAllTermCourseSets()){
            if(s.getTermId() == termId && s.getCourseId() == courseId){
                return true;
            }
        }
        return false;
    }

    //notification
    public void createNotificationTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS " + NOTIFICATION_TABLE_NAME + " (" +
                NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATION_TITLE + " TEXT, " +
                NOTIFICATION_SUB_TEXT + " TEXT, " +
                NOTIFICATION_TIME + " TEXT)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(createTable);
    }
    public boolean addNotification(Notification n){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFICATION_TITLE, n.getNotificationTitle());
        contentValues.put(NOTIFICATION_SUB_TEXT, n.getNotificationSubText());
        contentValues.put(NOTIFICATION_TIME, n.getNotificationTime().toString());

        long result = db.insert(NOTIFICATION_TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public void printAllNotificaitons(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + NOTIFICATION_TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append("\n");
            buffer.append(NOTIFICATION_ID + ": " + data.getString(0) + "\n");
            buffer.append(NOTIFICATION_TITLE + ": " + data.getString(1) + "\n");
            buffer.append(NOTIFICATION_SUB_TEXT + ": " + data.getString(2) + "\n");
            buffer.append(NOTIFICATION_TIME + ": " + data.getString(3) + "\n");

        }
        Log.v("----------------------", buffer.toString());
    }
    public void deleteAllNotifications(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTIFICATION_TABLE_NAME,null,null);
    }
    public ArrayList<Notification> getAllNotifications(){
        ArrayList<Notification> nots = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + NOTIFICATION_TABLE_NAME, null);

        while(data.moveToNext()){
            int id = Integer.parseInt(data.getString(0));
            Timestamp ts = Timestamp.valueOf(data.getString(3));
            nots.add(new Notification(id,
                    data.getString(1),
                    data.getString(2),
                    ts));
        }


        return nots;
    }
    public int getNextNotificationId(){
        int max = 0;
        for(Notification n : getAllNotifications()){
            if(n.getNotificationId()>max){
                max = n.getNotificationId();
            }
        }
        return max+1;
    }
}
