package com.engsoft.poli.upe.quitandaverdeapp.models;


public class CartItem {

    private int id;
    private int productId;
    private int qt;
    private Product product;

    public CartItem(int productId, int qt) {
        this.productId = productId;
        this.qt = qt;
    }

    public int getQuantity() {
        return qt;
    }
    public int getProductId() {
        return productId;
    }
    public int getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }

    public String getTotal(){ return this.qt + " x R$ " + this.product.getProductValue();}
    public void setId(int id) {
        this.id = id;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
