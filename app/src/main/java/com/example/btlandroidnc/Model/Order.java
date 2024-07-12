package com.example.btlandroidnc.Model;

public class Order {
    private String product_id;
    private int quantity;
    private float price;
    private float price_sale;

    public Order(String product_id, int quantity, float price, float price_sale) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.price_sale = price_sale;
    }

    public Order() {

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(float price_sale) {
        this.price_sale = price_sale;
    }
}
