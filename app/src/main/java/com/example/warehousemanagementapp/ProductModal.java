package com.example.warehousemanagementapp;

public class ProductModal
{
    // variables for our coursename,
    // description, tracks and duration, id.
    private String productSerial;
    private String productName;
    private String price;
    private String productQnt;
    private int id;

    // creating getter and setter methods
    public String getProductSerial() {
        return productSerial;
    }

    public void setProductSerial(String productSerial) {
        this.productSerial = productSerial;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductQnt() {
        return productQnt;
    }

    public void setProductQnt(String productQnt) {
        this.productQnt = productQnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public ProductModal(String productSerial, String productName,
                        String price,
                        String productQnt) {
        this.productSerial = productSerial;
        this.productName = productName;
        this.price = price;
        this.productQnt = productQnt;
    }
}
