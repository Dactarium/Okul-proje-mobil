package com.eren.siparisuygulamasi.items.order;

import java.io.Serializable;

public class Order implements Serializable {
    public String id;
    public String name;
    public int amount;
    public float price;
    float total;

    public Order(){}

    public Order(String id, String name, int amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.total = amount * price;
    }

    public Order(String name, int amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.total = amount * price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        total = price * amount;
    }

    public void increaseAmount() {
        amount++;
        total = price * amount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
        total = price * this.amount;
    }

    public float getTotal(){return total;}

    public float getPrice() {return price;}

    public int getAmount(){return amount;}

    public String getId() {return id;}
}
