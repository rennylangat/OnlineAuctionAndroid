package com.example.goingonce.models;

public class RecommendedItems {
    private String itemID;
    private String itemName;
    private String itemPrice;
    private String deliveryTime;
    private String deliveryType;
    private String itemType;
    private String imageUrl;

    public RecommendedItems(){
    }

    public RecommendedItems(String itemID,String itemName, String itemPrice, String deliveryTime, String deliveryType,String itemType,String imageUrl) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.itemType=itemType;
        this.imageUrl=imageUrl;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RecommendedItems{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", itemType='" + itemType + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
