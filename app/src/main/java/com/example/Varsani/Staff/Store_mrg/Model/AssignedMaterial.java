package com.example.Varsani.Staff.Store_mrg.Model;

public class AssignedMaterial {
    private String materialName;
    private String quantity;
    private String unit;

    public AssignedMaterial(String materialName, String quantity, String unit) {
        this.materialName = materialName;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getMaterialName() { return materialName; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}
