package com.engsoft.poli.upe.quitandaverdeapp.models;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private Double value;
    private int id;

    public Product(String productName, Double value) {
        this.name = productName;
        this.value = value;
    }

    public String getProductName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public Double getProductValue() {
        return value;
    }
    public void setId(int id) {
        this.id = id;
    }

}
