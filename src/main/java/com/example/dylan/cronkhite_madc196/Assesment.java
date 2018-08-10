package com.example.dylan.cronkhite_madc196;

public class Assesment {
    private int assesmentID;
    private String assesmentTitle;
    private String dueDate;
    private int courseID;

    public Assesment(int assesmentID, String assesmentTitle, String dueDate, int courseID) {
        this.assesmentID = assesmentID;
        this.assesmentTitle = assesmentTitle;
        this.dueDate = dueDate;
        this.courseID = courseID;
    }

    public int getAssesmentID() {
        return assesmentID;
    }

    public void setAssesmentID(int assesmentID) {
        this.assesmentID = assesmentID;
    }

    public String getAssesmentTitle() {
        return assesmentTitle;
    }

    public void setAssesmentTitle(String assesmentTitle) {
        this.assesmentTitle = assesmentTitle;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
