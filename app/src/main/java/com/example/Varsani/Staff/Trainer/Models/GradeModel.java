package com.example.Varsani.Staff.Trainer.Models;

public class GradeModel {
    private String traineeId;
    private String traineeName;
    private String examMarks;
    private String theoryMarks;
    private String assignmentMarks; // New field for assignment marks

    // Updated constructor with assignmentMarks
    public GradeModel(String traineeId, String traineeName, String examMarks, String assignmentMarks) {
        this.traineeId = traineeId;
        this.traineeName = traineeName;
        this.examMarks = examMarks;
        this.assignmentMarks = assignmentMarks;
    }

    // Getter and Setter for traineeId
    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    // Getter and Setter for traineeName
    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    // Getter and Setter for examMarks
    public String getExamMarks() {
        return examMarks;
    }

    public void setExamMarks(String examMarks) {
        this.examMarks = examMarks;
    }

    // Getter and Setter for theoryMarks
    public String getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(String theoryMarks) {
        this.theoryMarks = theoryMarks;
    }

    // Getter and Setter for assignmentMarks
    public String getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(String assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }
}
