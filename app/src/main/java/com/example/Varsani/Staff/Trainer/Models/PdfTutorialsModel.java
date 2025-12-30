package com.example.Varsani.Staff.Trainer.Models;

public class PdfTutorialsModel {
    private String id;
    private String subjectId;
    private String subjectName;
    private String title;
    private String pdfLink;
    private String datePosted;
    private String details;

    public PdfTutorialsModel(String id, String subjectId, String subjectName,
                             String title, String pdfLink, String datePosted, String details) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.title = title;
        this.pdfLink = pdfLink;
        this.datePosted = datePosted;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTitle() {
        return title;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getDetails() {
        return details;
    }
}

