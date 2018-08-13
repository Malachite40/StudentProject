package com.example.dylan.cronkhite_madc196;

public class TermCourseSet {
    private int termCourseId;
    private int termId;
    private int courseId;

    public TermCourseSet(int termCourseId, int termId, int courseId) {
        this.termCourseId = termCourseId;
        this.termId = termId;
        this.courseId = courseId;
    }

    public int getTermCourseId() {
        return termCourseId;
    }

    public void setTermCourseId(int termCourseId) {
        this.termCourseId = termCourseId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
