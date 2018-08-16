package com.example.dylan.cronkhite_madc196;

import java.sql.Timestamp;

public class Notification {
    int notificationId;
    String notificationTitle;
    String notificationSubText;
    Timestamp notificationTime;

    public Notification(int notificationId, String notificationTitle, String notificationSubText, Timestamp notificationTime) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationSubText = notificationSubText;
        this.notificationTime = notificationTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationSubText() {
        return notificationSubText;
    }

    public void setNotificationSubText(String notificationSubText) {
        this.notificationSubText = notificationSubText;
    }

    public Timestamp getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Timestamp notificationTime) {
        this.notificationTime = notificationTime;
    }
}
