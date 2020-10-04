package com.example.firestore.Modal;

public class Cart {

    private String  price;
    private String quantity;
    private String name;
    private String link;


    public Cart(String price, String quantity, String name, String link) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
        this.link = link;
    }

    public Cart(){
        //empty for Firestore
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

}
