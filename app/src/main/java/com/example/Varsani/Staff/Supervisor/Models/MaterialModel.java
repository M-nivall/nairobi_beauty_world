package com.example.Varsani.Staff.Supervisor.Models;

// MaterialModel.java
public class MaterialModel {
    private String material_name, quantity, unit;

    public MaterialModel(String material_name, String quantity, String unit) {
        this.material_name = material_name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getMaterialName() { return material_name; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}
