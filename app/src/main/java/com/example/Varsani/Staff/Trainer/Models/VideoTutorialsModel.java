package com.example.Varsani.Staff.Trainer.Models;

public class VideoTutorialsModel {
    private String id;
    private String subjectId;
    private String subjectName;
    private String title;
    private String videoLink;
    private String datePosted;
    private String details;

    public VideoTutorialsModel(String id, String subjectId, String subjectName,
                               String title, String videoLink, String datePosted, String details) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.title = title;
        this.videoLink = videoLink;
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

    public String getVideoLink() {
        return videoLink;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getDetails() {
        return details;
    }
}
