package com.example.Varsani.Staff.ServMrg.Adapter.Models;

public class CompletedTraineeModel {
    private String traineeId;
    private String traineeNames;
    private String assignMarks;
    private String examMarks;
    private String practicalMarks;
    private String finalScore;

    public CompletedTraineeModel(String traineeId, String traineeNames, String assignMarks, String examMarks, String practicalMarks,
                                 String finalScore) {
        this.traineeId = traineeId;
        this.traineeNames = traineeNames;
        this.assignMarks = assignMarks;
        this.examMarks = examMarks;
        this.practicalMarks = practicalMarks;
        this.finalScore = finalScore;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getTraineeNames() {
        return traineeNames;
    }

    public void setTraineeNames(String traineeNames) {
        this.traineeNames = traineeNames;
    }
    public String getAssignMarks() {return assignMarks;}
    public void setAssignMarks(String assignMarks) {
        this.assignMarks = assignMarks;
    }

    public String getExamMarks() {
        return examMarks;
    }

    public void setExamMarks(String examMarks) {
        this.examMarks = examMarks;
    }

    public String getPracticalMarks() {
        return practicalMarks;
    }

    public void setPracticalMarks(String practicalMarks) {
        this.practicalMarks = practicalMarks;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }



}
