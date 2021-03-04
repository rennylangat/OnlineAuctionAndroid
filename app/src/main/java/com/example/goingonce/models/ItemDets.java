package com.example.goingonce.models;

public class ItemDets {
    String itemName;
    String itemPrice;
    String itemDesc;
    String itemImg;

    public ItemDets(String itemName, String itemPrice, String itemDesc, String itemImg) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDesc = itemDesc;
        this.itemImg = itemImg;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    @Override
    public String toString() {
        return "ItemDets{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemImg='" + itemImg + '\'' +
                '}';
    }
}
