package com.example.Varsani.Clients.Models;

public class BookingHistoryModel {
    private String bookingID;
    private String clientName;
    private String phoneNo;
    private String course;
    private String studyMode;
    private String fee;
    private String duration;
    private String startingDate;
    private String paymentMethod;
    private String paymentCode;
    private String bookingDate;
    private String bookingStatus;
    private  String remarks;

    // Updated constructor to include all fields
    public BookingHistoryModel(String bookingID, String clientName, String phoneNo, String course, String studyMode, String fee,
                               String duration, String startingDate, String paymentMethod, String paymentCode,
                               String bookingDate, String bookingStatus, String remarks) {
        this.bookingID = bookingID;
        this.clientName = clientName;
        this.phoneNo = phoneNo;
        this.course = course;
        this.studyMode = studyMode;
        this.paymentMethod = paymentMethod;
        this.duration = duration;
        this.startingDate = startingDate;
        this.fee = fee;
        this.paymentCode = paymentCode;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.remarks = remarks;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getClientName() {
        return clientName;
    }

    public String getPhoneNo() {
        return phoneNo;
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
    public String getBookingStatus() {
        return bookingStatus;
    }
    public String getRemarks() {
        return remarks;
    }
}
