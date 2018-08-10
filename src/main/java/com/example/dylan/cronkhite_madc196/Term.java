package com.example.dylan.cronkhite_madc196;

public class Term {
    private int termID;
    private String startDate;
    private String endDate;
    private String termName;

    public Term(int termID, String startDate, String endDate, String termName) {
        this.termID = termID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termName = termName;
    }

    public int getTermID() {
        return termID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
