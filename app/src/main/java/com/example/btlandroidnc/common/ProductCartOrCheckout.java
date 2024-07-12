package com.example.btlandroidnc.common;

import com.example.btlandroidnc.Model.Product;

public class ProductCartOrCheckout {
    public int quantity;

    public Product product;


    public ProductCartOrCheckout(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


}
