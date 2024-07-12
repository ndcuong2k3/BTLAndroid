package com.example.btlandroidnc.Model;

public class Product {
    private String id;
    private String name;
    private String description;
    private int sold;
    private int warehouse;
    private float price;
    private float price_sale;
    private String image;
    private String category;
    private String brand;
    private String color;

    public Product(String id, String name, String description, int sold, int warehouse, float price, float price_sale, String image, String category, String brand, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sold = sold;
        this.warehouse = warehouse;
        this.price = price;
        this.price_sale = price_sale;
        this.image = image;
        this.category = category;
        this.brand = brand;
        this.color = color;
    }

    public Product() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sold=" + sold +
                ", warehouse=" + warehouse +
                ", price=" + price +
                ", price_sale=" + price_sale +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
