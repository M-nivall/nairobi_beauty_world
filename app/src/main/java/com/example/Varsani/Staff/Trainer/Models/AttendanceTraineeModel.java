package com.example.Varsani.Staff.Trainer.Models;

public class AttendanceTraineeModel {
    private String traineeId;
    private String traineeName;

    // Constructor
    public AttendanceTraineeModel(String traineeId, String traineeName) {
        this.traineeId = traineeId;
        this.traineeName = traineeName;
    }

    // Getters
    public String getTraineeId() {
        return traineeId;
    }

    public String getTraineeName() {
        return traineeName;
    }
}
