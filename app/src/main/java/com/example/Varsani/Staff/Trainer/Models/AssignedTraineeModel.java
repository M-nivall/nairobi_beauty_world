package com.example.Varsani.Staff.Trainer.Models;

public class AssignedTraineeModel {
    private String traineeId;
    private String traineeName;
    private String email;
    private String phoneNo;
    private String startingDate;

    public AssignedTraineeModel(String traineeId, String traineeName, String email, String phoneNo, String startingDate) {
        this.traineeId = traineeId;
        this.traineeName = traineeName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.startingDate = startingDate;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
    public String getStartingDate() {
        return startingDate;
    }
}
