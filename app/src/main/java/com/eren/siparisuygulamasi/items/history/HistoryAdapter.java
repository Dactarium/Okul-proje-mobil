package com.eren.siparisuygulamasi.items.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(Context context, ArrayList<History> histories){
        super(context, 0, histories);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        History history = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history, parent, false);
        }

        TextView date_text = (TextView) convertView.findViewById(R.id.text_date);
        TextView restaurant_name_text = (TextView) convertView.findViewById(R.id.text_name);
        TextView total_text = (TextView) convertView.findViewById(R.id.text_total);

        date_text.setText(getDateString(history.date));
        restaurant_name_text.setText(history.restaurant_name);
        total_text.setText(Float.toString(history.total));

        return convertView;
    }

    private String getDateString(Timestamp timestamp){
        String date  = "";
        Date tsDate = timestamp.toDate();
        date = addZero(Integer.toString(tsDate.getDate())) + "/" + addZero(Integer.toString(tsDate.getMonth()+1)) + "/" + Integer.toString(tsDate.getYear() + 1900);
        return date;
    }

    private String addZero(String string){
        if(string.length() == 1) string = "0" + string;
        return string;
    }
}
