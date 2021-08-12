package com.eren.siparisuygulamasi.items.order;

import java.io.Serializable;

public class Order implements Serializable {
    public String name;
    int amount;
    float price;
    float total;

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
        this.amount++;
        total = price * amount;
    }

    public float getTotal(){
        return total;
    }
}
