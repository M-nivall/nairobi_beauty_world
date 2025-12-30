package com.example.Varsani.Clients.Models;

public class ClassAttendanceModel {
    private String id;
    private String topic;
    private String instructorID;
    private String instructorName;
    private String sessionDate;

    public ClassAttendanceModel(String id, String topic, String instructorID, String instructorName, String sessionDate) {
        this.id = id;
        this.topic = topic;
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.sessionDate = sessionDate;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getSessionDate() {
        return sessionDate;
    }
}
