package com.example.regalo;

public class Attendance {
    String presenttext;
    String RecAttend;

    public Attendance() {
    }

    public Attendance(String presenttext, String recAttend) {
        this.presenttext = presenttext;
        RecAttend = recAttend;
    }

    public String getPresenttext() {
        return presenttext;
    }

    public void setPresenttext(String presenttext) {
        this.presenttext = presenttext;
    }

    public String getRecAttend() {
        return RecAttend;
    }

    public void setRecAttend(String recAttend) {
        RecAttend = recAttend;
    }
}
