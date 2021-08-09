package com.eren.siparisuygulamasi.items.history;

public class History {
    public String date;
    public String name;
    public float total;

    public History(String access_code, String date, String name, float total){
        this.date = date;
        this.name = name;
        this.total = total;
    }
}
