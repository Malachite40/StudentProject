package com.example.dylan.cronkhite_madc196;

public class EnabledTerm {
    public EnabledTerm(int courseId,boolean enabled, String courseName) {
        this.courseId = courseId;
        this.enabled = enabled;
        this.courseName = courseName;
    }
    public int courseId;
    public Boolean enabled = false;
    public String courseName;
}
