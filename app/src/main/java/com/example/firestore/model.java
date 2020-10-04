package com.example.firestore;

public class model
{
    public double price;
    public String name;

    public model()
    {

    }

    public model(double price, String name)
    {
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
