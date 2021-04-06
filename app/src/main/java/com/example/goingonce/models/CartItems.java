package com.example.goingonce.models;

public class CartItems {
    private String itemID,itemName,description,type,itemPrice,location,endTime,imageUrl;

    public CartItems(){

    }

    public CartItems(String itemID, String itemName, String description, String type, String itemPrice, String location, String endTime, String imageUrl) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.type = type;
        this.itemPrice = itemPrice;
        this.location = location;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", location='" + location + '\'' +
                ", endTime='" + endTime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
