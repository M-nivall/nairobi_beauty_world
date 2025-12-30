package com.example.Varsani.Staff.Store_mrg.Model;

public class StockModel {
    private String stockID;
    private String productName;
    private String price;
    private String stock;

    public StockModel(String stockID, String productName, String price, String stock) {
        this.stockID = stockID;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public String getStockID() {
        return stockID;
    }

    public String getProductName() {
        return productName;
    }
    public String getPrice() {
        return price;
    }
    public String getStock() {
        return stock;
    }
}
