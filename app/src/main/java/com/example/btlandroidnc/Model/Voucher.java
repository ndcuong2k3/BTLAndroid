package com.example.btlandroidnc.Model;

public class Voucher {
    private String id;
    private String title;
    private String description;
    private String image;
    private float value;
    private float condition;

    public String getId(){
        return id;
    }

    public void setId(String i){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getCondition() {
        return condition;
    }

    public void setCondition(float condition) {
        this.condition = condition;
    }
    public Voucher() {

    }
    public Voucher(String title, String description, String image, float value, float condition) {
    }

    public Voucher(String id, String title, String description, String image, float value, float condition) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.value = value;
        this.condition = condition;
    }
}
