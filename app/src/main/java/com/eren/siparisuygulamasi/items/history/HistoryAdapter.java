package com.eren.siparisuygulamasi.items.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;

import java.util.ArrayList;

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
        TextView name_text = (TextView) convertView.findViewById(R.id.text_name);
        TextView total_text = (TextView) convertView.findViewById(R.id.text_total);

        date_text.setText(history.date);
        name_text.setText(history.name);
        total_text.setText(Float.toString(history.total));

        return convertView;
    }
}
