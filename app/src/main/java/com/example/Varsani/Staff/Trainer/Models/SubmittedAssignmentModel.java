package com.example.Varsani.Staff.Trainer.Models;

public class SubmittedAssignmentModel {
    private String trainee_id;
    private String names;
    private String pdfLink;
    private  String state;

    public SubmittedAssignmentModel(String trainee_id, String names, String pdfLink, String state) {
        this.trainee_id = trainee_id;
        this.names = names;
        this.pdfLink = pdfLink;
        this.state = state;

    }

    public String getTrainee_id() {
        return trainee_id;
    }

    public String getNames() {
        return names;
    }

    public String getPdfLink() { return pdfLink; }

    public String getState() {return state; }


}
