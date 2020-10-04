package com.example.firestore.Modal;

public class Order
{
    public String address;
    public String map_address;
    public String name;
    public String phone;
    public Double price;
    public String delivery_when;

    public Order(String address, String map_address, String name, String phone, Double price, String delivery_when) {
        this.address = address;
        this.map_address = map_address;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.delivery_when = delivery_when;
    }

    public Order() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMap_address() {
        return map_address;
    }

    public void setMap_address(String map_address) {
        this.map_address = map_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDelivery_when() {
        return delivery_when;
    }

    public void setDelivery_when(String delivery_when) {
        this.delivery_when = delivery_when;
    }
}
