package com.example.Varsani.Staff.Supervisor.Models;

import java.util.List;

public class TrackProductionModel {
    private String batch_no, product, produceQty, productionDate, productionStatus, techName, techNo, producedQuantity;
    private List<MaterialModel> materials;

    public TrackProductionModel(String batch_no, String product, String produceQty, String productionDate, String productionStatus, String techName, String techNo, String producedQuantity, List<MaterialModel> materials) {
        this.batch_no = batch_no;
        this.product = product;
        this.materials = materials;
        this.produceQty = produceQty;
        this.productionDate = productionDate;
        this.productionStatus = productionStatus;
        this.techName = techName;
        this.techNo = techNo;
        this.producedQuantity = producedQuantity;
    }

    public String getBatchNo() { return batch_no; }
    public String getProduct() { return product; }
    public String getProduceQty() { return produceQty; }
    public String getProductionDate() { return productionDate; }
    public String getProductionStatus() { return productionStatus; }
    public String getTechName() { return techName; }
    public String getTechNo() { return techNo; }
    public String getProducedQuantity() { return producedQuantity; }
    public List<MaterialModel> getMaterials() { return materials; }
}
