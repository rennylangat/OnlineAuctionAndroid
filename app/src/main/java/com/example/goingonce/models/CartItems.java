package com.example.goingonce.models;

public class CartItems {
    String itemID,itemPrice;

    public CartItems(String itemID, String itemPrice) {
        this.itemID = itemID;
        this.itemPrice = itemPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
