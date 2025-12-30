package com.example.Varsani.Staff.Models;

public class BookingModel {
    String bookingID;
    String clientID;
    String clientName;
    String course;
    String studyMode;
    String fee;
    String duration;
    String startingDate;
    String paymentMethod;
    String paymentCode;
    String bookingDate;
    String phoneNo;
    String bookingStatus;

    public BookingModel(String bookingID, String clientID, String clientName,
                            String course, String studyMode, String fee,String duration,String startingDate
            ,String paymentMethod,String paymentCode,String bookingDate, String phoneNo, String bookingStatus){
        this.bookingID=bookingID ;
        this.clientID=clientID ;
        this.clientName=clientName ;
        this.course =course ;
        this.studyMode=studyMode ;
        this.fee=fee ;
        this.duration=duration ;
        this.startingDate=startingDate ;
        this.paymentMethod=paymentMethod ;
        this.paymentCode=paymentCode ;
        this.bookingDate=bookingDate ;
        this.phoneNo = phoneNo;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientName() {
        return clientName;
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
    public String getPhoneNo() {
        return phoneNo;
    }
    public String getBookingStatus() {
        return bookingStatus;
    }
}
