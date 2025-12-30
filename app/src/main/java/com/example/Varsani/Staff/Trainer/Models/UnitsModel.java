package com.example.Varsani.Staff.Trainer.Models;

public class UnitsModel {

    private String  unitId;
    private String unitName;
    private String unitStatus;

    public UnitsModel(String unitId, String unitName, String unitStatus) {
        this.unitId =unitId;
        this.unitName = unitName;
        this.unitStatus = unitStatus;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitStatus() {
        return unitStatus;
    }
}
