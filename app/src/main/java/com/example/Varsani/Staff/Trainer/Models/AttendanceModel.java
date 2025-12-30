package com.example.Varsani.Staff.Trainer.Models;

public class AttendanceModel {
    private String id;
    private String topic;
    private String sessionDate;

    // Constructor
    public AttendanceModel(String id, String topic, String sessionDate) {
        this.id = id;
        this.topic = topic;
        this.sessionDate = sessionDate;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getSessionDate() {
        return sessionDate;
    }
}
