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

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getName() {
        return name;
    }

    public String getUniqueId() {
        return uniqueId;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalCost() {
        return delivery_fee + price ;
    }

    public void setTotalCost(int totalCost) {
        TotalCost = totalCost;
    }
}
