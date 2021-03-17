package com.example.goingonce.activities;

public class Payment {
    private String type, value;

    public Payment() {
    }

    public Payment(String tittle, String value) {
        this.type = tittle;
        this.value = value;
    }

    public String getTittle() {
        return type;
    }

    public void setTittle(String tittle) {
        this.type = tittle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
