package com.example.regalo;

public class Attendance {
    String attendanceName, attendanceAttend;

    public Attendance() {
    }

    public Attendance(String attendanceName, String attendanceAttend) {
        this.attendanceName = attendanceName;
        this.attendanceAttend = attendanceAttend;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public String getAttendanceAttend() {
        return attendanceAttend;
    }

    public void setAttendanceAttend(String attendanceAttend) {
        this.attendanceAttend = attendanceAttend;
    }
}
