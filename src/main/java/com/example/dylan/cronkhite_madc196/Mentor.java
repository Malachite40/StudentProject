package com.example.dylan.cronkhite_madc196;

public class Mentor {
    private int mentorID;
    private String mentorName;
    private String email;
    private String phone;

    public Mentor(int mentorID, String mentorName, String email, String phone) {
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.email = email;
        this.phone = phone;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
