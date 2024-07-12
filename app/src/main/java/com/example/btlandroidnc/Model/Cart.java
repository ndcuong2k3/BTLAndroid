package com.example.btlandroidnc.Model;

import java.util.HashMap;

public class Cart {
    // key is id of product
    // value is quantity
    private HashMap<String, Integer> products;

    public Cart(HashMap<String, Integer> products) {
        this.products = products;
    }

    public Cart(){
        this.products = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> get_products() {
        return products;
    }

    public void set_products(HashMap<String, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                '}';
    }
}
