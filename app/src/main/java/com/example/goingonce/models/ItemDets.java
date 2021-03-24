package com.example.goingonce.models;

public class ItemDets {
    private String itemID,itemName,description,type,baseBid,location,endTime,imageUrl;

    public ItemDets(){

    }

    public ItemDets(String itemID,String itemName, String description,String type, String baseBid, String location, String endTime, String imageUrl) {
        this.itemID=itemID;
        this.itemName = itemName;
        this.description = description;
        this.type=type;
        this.baseBid = baseBid;
        this.location = location;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
    }

    public String getItemID(){
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

    public String getBaseBid() {
        return baseBid;
    }

    public void setBaseBid(String baseBid) {
        this.baseBid = baseBid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ItemDets{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", baseBid='" + baseBid + '\'' +
                ", location='" + location + '\'' +
                ", endTime='" + endTime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
