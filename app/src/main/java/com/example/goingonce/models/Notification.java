package com.example.goingonce.models;

public class Notification {
    public String title;
    public String body;
    public String itemID;
    public String username;
    public String itemPrice;
    public String dropOff;

    public Notification(){

    }

    public Notification(String title, String body, String itemID, String username, String itemPrice, String dropOff) {
        this.title = title;
        this.body = body;
        this.itemID = itemID;
        this.username = username;
        this.itemPrice = itemPrice;
        this.dropOff = dropOff;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }
}
