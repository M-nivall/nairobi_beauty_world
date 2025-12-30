package com.example.Varsani.Staff.ServMrg.Adapter.Models;

public class CompletedHistoryModel {
    private String bookingID;
    private String clientID;
    private String course;
    private String studyMode;
    private String fee;
    private String duration;
    private String traineeNames;
    private String startingDate;
    private String paymentMethod;
    private String paymentCode;
    private String bookingDate;
    private String rating;
    private String examMarks;
    private String practicalMarks;
    private String assignmentMarks;
    private String finalScore;
    private  String certificateNo;
    private String phoneNo;

    // Constructor
    public CompletedHistoryModel(String bookingID, String clientID, String course, String studyMode,
                                 String fee, String duration, String traineeNames,
                                 String startingDate, String paymentMethod, String paymentCode,
                                 String bookingDate, String rating,
                                 String examMarks, String practicalMarks, String assignmentMarks,
                                 String finalScore, String certificateNo, String phoneNo) {
        this.bookingID = bookingID;
        this.clientID = clientID;
        this.course = course;
        this.studyMode = studyMode;
        this.fee = fee;
        this.duration = duration;
        this.traineeNames = traineeNames;
        this.startingDate = startingDate;
        this.paymentMethod = paymentMethod;
        this.paymentCode = paymentCode;
        this.bookingDate = bookingDate;
        this.rating = rating;
        this.examMarks = examMarks;
        this.practicalMarks = practicalMarks;
        this.assignmentMarks = assignmentMarks;
        this.finalScore = finalScore;
        this.certificateNo = certificateNo;
        this.phoneNo = phoneNo;
    }

    // Getter methods
    public String getBookingID() {
        return bookingID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getCourse() {
        return course;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public String getFee() {
        return fee;
    }

    public String getDuration() {
        return duration;
    }

    public String getTraineeNames() {
        return traineeNames;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getRating() {
        return rating;
    }

    public String getExamMarks() {
        return examMarks;
    }

    public String getPracticalMarks() {
        return practicalMarks;
    }
    public String getAssignmentMarks() {
        return assignmentMarks;
    }
    public String getFinalScore() {
        return finalScore;
    }
    public String getCertificateNo() {
        return certificateNo;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
}
