package com.example.dylan.cronkhite_madc196;

public class CourseMentorSet {
    private int courseMentorSetID;
    private int mentorID;
    private int courseID;

    public CourseMentorSet(int courseMentorSetID, int mentorID, int courseID) {
        this.courseMentorSetID = courseMentorSetID;
        this.mentorID = mentorID;
        this.courseID = courseID;
    }

    public int getCourseMentorSetID() {
        return courseMentorSetID;
    }

    public void setCourseMentorSetID(int courseMentorSetID) {
        this.courseMentorSetID = courseMentorSetID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
