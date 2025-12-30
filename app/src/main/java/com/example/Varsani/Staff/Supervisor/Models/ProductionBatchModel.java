package com.example.Varsani.Staff.Supervisor.Models;

import java.util.List;

// ProductionBatchModel.java
public class ProductionBatchModel {
    private String batch_no, product, produceQty, productionDate, productionStatus;
    private List<MaterialModel> materials;

    public ProductionBatchModel(String batch_no, String product, String produceQty, String productionDate, String productionStatus, List<MaterialModel> materials) {
        this.batch_no = batch_no;
        this.product = product;
        this.materials = materials;
        this.produceQty = produceQty;
        this.productionDate = productionDate;
        this.productionStatus = productionStatus;
    }

    public String getBatchNo() { return batch_no; }
    public String getProduct() { return product; }
    public String getProduceQty() { return produceQty; }
    public String getProductionDate() { return productionDate; }
    public String getProductionStatus() { return productionStatus; }
    public List<MaterialModel> getMaterials() { return materials; }
}


