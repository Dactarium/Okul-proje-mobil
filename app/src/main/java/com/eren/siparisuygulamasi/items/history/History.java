package com.eren.siparisuygulamasi.items.history;

public class History {
    public String access_code;
    public String date;
    public String name;
    public float total;

    public History(String access_code, String date, String name, float total){
        this.access_code = access_code;
        this.date = date;
        this.name = name;
        this.total = total;
    }
}
