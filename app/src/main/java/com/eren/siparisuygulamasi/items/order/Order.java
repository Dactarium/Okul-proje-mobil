package com.eren.siparisuygulamasi.items.order;

public class Order {
    String name;
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
}
