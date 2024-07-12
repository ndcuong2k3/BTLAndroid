package com.example.btlandroidnc.Model;

import java.util.Date;

public class User_Notification {
    String id;
    String name;
    String content;
    Date date;

    public User_Notification() {
    }

    public User_Notification(String id, String name, String content, Date date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
