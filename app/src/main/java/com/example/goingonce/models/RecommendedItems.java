package com.example.goingonce.models;

public class RecommendedItems {
    String itemName,itemPrice,deliveryTime,deliveryType;

    public RecommendedItems(){

    }

    public RecommendedItems(String itemName, String itemPrice, String deliveryTime, String deliveryType) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
