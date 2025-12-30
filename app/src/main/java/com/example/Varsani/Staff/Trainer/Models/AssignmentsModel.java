package com.example.Varsani.Staff.Trainer.Models;

public class AssignmentsModel {
    private String id;
    private String date_posted;
    private String title;
    private  String pdfLink;

    public AssignmentsModel(String id, String date_posted, String title, String pdfLink) {
        this.id = id;
        this.date_posted = date_posted;
        this.title = title;
        this.pdfLink = pdfLink;

    }

    public String getId() {
        return id;
    }
    public String getDatePosted() {
        return date_posted;
    }

    public String getTitle() {
        return title;
    }
    public String getPdfLink() {
        return pdfLink;
    }


}
