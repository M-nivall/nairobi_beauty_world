package com.example.Varsani.Staff.Models;

public class OrderToShipModel {
    String orderID;
    String orderStatus;
    String clientName;
    String orderDate;
    String county;
    String town;
    String address;
    String orderCost;
    String deliveryCost;
    String totalCost;

    public OrderToShipModel(String orderID, String clientName, String orderStatus, String orderDate
                            , String county, String town, String address, String orderCost, String deliveryCost, String totalCost){
        this.orderID=orderID ;
        this.clientName=clientName ;
        this.orderStatus=orderStatus ;
        this.orderDate=orderDate ;
        this.county=county ;
        this.town=town ;
        this.address=address ;
        this.orderCost=orderCost;
        this.deliveryCost=deliveryCost;
        this.totalCost=totalCost;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getClientName() {
        return clientName;
    }


    public String getOrderDate() {
        return orderDate;
    }


    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public String getAddress() {
        return address;
    }
    public String getOrderCost() {
        return orderCost;
    }
    public String getDeliveryCost() {
        return deliveryCost;
    }
    public String getTotalCost() {
        return totalCost;
    }
}
