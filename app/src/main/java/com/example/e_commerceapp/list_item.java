package com.example.e_commerceapp;

public class list_item {
    int image;
    String name;
    Double price;
    int quantity;
    String date;
    int id;

    public list_item(int id,int image, String name, Double price, int quantity,String date) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
