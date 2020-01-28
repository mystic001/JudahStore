package com.example.judahstore;

import androidx.lifecycle.AndroidViewModel;

public class ProductViewModel {

    private productModel prodel;

    public ProductViewModel(productModel prodel){
        this.prodel = prodel;
    }

    public String getProName(){
        return prodel.getName();
    }

    public productModel getProdel(){

        return prodel;
    }

    public void setProdel(productModel mod){
        prodel = mod ;
    }

    public String getProUrl(){
        return  prodel.getImageUrl();
    }

    public String getProDescription(){
        return  prodel.getDescription();
    }

    public int getPrice(){
        return  prodel.getPrice();
    }

    public int getDelivery(){
        return prodel.getDelivery_fee();
    }

    public int getTotal(){
        return  prodel.getTotalCost();
    }




}
