package com.example.goingonce.models;

public class PopularItems {
    private String itemID;
    private String itemName;
    private String imageUrl;
    private String itemPrice;

    public PopularItems(){

    }
    public PopularItems(String itemID, String itemName, String imageUrl, String itemPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.itemPrice = itemPrice;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "PopularItems{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                '}';
    }
}
