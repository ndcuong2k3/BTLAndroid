package com.example.btlandroidnc.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Date date_of_birth;
    private String phone;
    private ArrayList<Voucher> vouchers;

    private ArrayList<User_Notification> user_notifications;

    public ArrayList<User_Notification> getUser_notifications() {
        return user_notifications;
    }

    private Cart cart;

    public void setUser_notifications(ArrayList<User_Notification> user_notifications) {
        this.user_notifications = user_notifications;
    }



    public User(String id, String name, String email, String password, Date date_of_birth, String phone, ArrayList<Voucher> vouchers, ArrayList<User_Notification> user_notifications, ArrayList<Voucher> arrayList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.vouchers = vouchers;
        this.user_notifications = user_notifications;
        this.cart = new Cart();
    }
    public User(String id, String name, String email, String password, String phone, ArrayList<Voucher> vouchers, ArrayList<User_Notification> user_notifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

        this.phone = phone;
        this.vouchers = vouchers;
        this.user_notifications = user_notifications;
        this.cart = new Cart();
    }

    public User(String id, String name, String email, String password, Date date_of_birth, String phone, ArrayList<Voucher> vouchers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.vouchers = vouchers;
        this.cart = new Cart();
    }

    public User() {
        this.cart = new Cart();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Voucher> getVouchers() {
        if (vouchers == null) {
            return new ArrayList<Voucher>();
        }
        return vouchers;
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void add_to_cart(String product_id) {
        if (this.cart == null) {
            this.cart = new Cart();
        }

        HashMap<String, Integer> products = this.cart.get_products();

        if (products.containsKey(product_id)) {
            int value = products.get(product_id);

            products.put(product_id, value + 1);
        } else {
            products.put(product_id, 1);
        }

        this.cart.set_products(products);
    }

    public void minus_to_cart(String product_id) {
        if (this.cart == null) {
            this.cart = new Cart();
            return;
        }

        HashMap<String, Integer> products = this.cart.get_products();
        if (products.containsKey(product_id)) {
            int value = products.get(product_id);

            if (value <= 1)
                products.remove(product_id);
            else
                products.put(product_id, value - 1);

            this.cart.set_products(products);
        }
    }

    public void delete_product(String product_id) {
        if (cart == null) {
            return;
        }

        HashMap<String, Integer> products = this.cart.get_products();

        products.remove(product_id);

        this.cart.set_products(products);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", phone='" + phone + '\'' +
                ", vouchers=" + vouchers +
                ", user_notifications=" + user_notifications +
                ", cart=" + cart.toString() +
                '}';
    }
}
