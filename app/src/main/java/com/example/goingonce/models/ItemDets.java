package com.example.goingonce.models;

public class ItemDets {
    private String itemId,itemName,description,baseBid,startTime,endTime,imageUrl;

    public ItemDets(){

    }

    public ItemDets(String itemId,String itemName, String description, String baseBid, String startTime, String endTime, String imageUrl) {
        this.itemId=itemId;
        this.itemName = itemName;
        this.description = description;
        this.baseBid = baseBid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
    }

    public String getItemId(){
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
        return "ItemDets{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", baseBid='" + baseBid + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
