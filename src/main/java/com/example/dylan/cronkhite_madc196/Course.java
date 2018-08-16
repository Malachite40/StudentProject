package com.example.dylan.cronkhite_madc196;

public class Course {
    private int courseID;
    private String startDate;
    private String endDate;
    private int termID;
    private String courseName;
    private String courseDescription;
    private String courseCode;
    private boolean alertStartEnabled;
    private boolean alertEndEnabled;
    private String status;

    public Course(int courseID, String startDate, String endDate, int termID, String courseName, String courseDescription, String courseCode, boolean alertStartEnabled, boolean alertEndEnabled, String status) {
        this.courseID = courseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termID = termID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseCode = courseCode;
        this.alertStartEnabled = alertStartEnabled;
        this.alertEndEnabled = alertEndEnabled;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public boolean isAlertStartEnabled() {
        return alertStartEnabled;
    }

    public void setAlertStartEnabled(boolean alertStartEnabled) {
        this.alertStartEnabled = alertStartEnabled;
    }

    public boolean isAlertEndEnabled() {
        return alertEndEnabled;
    }

    public void setAlertEndEnabled(boolean alertEndEnabled) {
        this.alertEndEnabled = alertEndEnabled;
    }
}
