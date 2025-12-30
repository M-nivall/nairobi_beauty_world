package com.example.Varsani.Staff.Supervisor.Models;

public class ProductionTaskModel {
    private String product, batch_no, produce_qty, production_date, production_status;
    private String material_name, quantity, unit;

    public ProductionTaskModel(String product, String batch_no, String produce_qty,
                               String production_date, String production_status,
                               String material_name, String quantity, String unit) {
        this.product = product;
        this.batch_no = batch_no;
        this.produce_qty = produce_qty;
        this.production_date = production_date;
        this.production_status = production_status;
        this.material_name = material_name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getProduct() { return product; }
    public String getBatch_no() { return batch_no; }
    public String getProduce_qty() { return produce_qty; }
    public String getProduction_date() { return production_date; }
    public String getProduction_status() { return production_status; }
    public String getMaterial_name() { return material_name; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}
