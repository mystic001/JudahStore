package com.example.judahstore;

public class productModel {

    private String name;
    private String imageUrl;
    private String description;
    private int price;
    private int TotalCost;
    private int delivery_fee;
    private String uniqueId;



    public productModel(){

    }

    public productModel(String string) {
        name = string;
    }

    public productModel(String name, String description, String link, int price, int delivery, int total) {
        this.name = name;
        this.imageUrl = link;
        this.description = description;
        this.price = price;
        this.delivery_fee = delivery;
        TotalCost = total;
    }

    public int getDelivery_fee() {

        return delivery_fee;
    }

    public productModel(String name, String imageUrl, String description, int price, int delivery_fee) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.delivery_fee = delivery_fee;
    }

    public String getName() {
        return name;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getDescription() {
        return description;
    }


    public int getPrice() {
        return price;
    }


    public int getTotalCost() {
        return delivery_fee + price ;
    }

}
