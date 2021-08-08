package com.eren.siparisuygulamasi.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.history.History;
import com.eren.siparisuygulamasi.items.history.HistoryAdapter;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    ImageButton back_button;
    ListView history_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        back_button = (ImageButton) findViewById(R.id.button_back);
        history_list = (ListView) findViewById(R.id.list_history);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<History> histories = new ArrayList<History>();
        histories.add(new History("123", "01/01/2021", "Test1", 0.50f));
        histories.add(new History("456", "01/01/2021", "Test2", 20.01f));
        histories.add(new History("789", "01/01/2021", "Test3", 25.50f));
        histories.add(new History("123", "01/01/2021", "Test1", 0.50f));
        histories.add(new History("456", "01/01/2021", "Test2", 20.01f));
        histories.add(new History("789", "01/01/2021", "Test3", 25.50f));
        histories.add(new History("123", "01/01/2021", "Test1", 0.50f));
        histories.add(new History("456", "01/01/2021", "Test2", 20.01f));
        histories.add(new History("789", "01/01/2021", "Test3", 25.50f));
        histories.add(new History("123", "01/01/2021", "Test1", 0.50f));
        histories.add(new History("456", "01/01/2021", "Test2", 20.01f));
        histories.add(new History("789", "01/01/2021", "Test3", 25.50f));
        histories.add(new History("123", "01/01/2021", "Test1", 0.50f));
        histories.add(new History("456", "01/01/2021", "Test2", 20.01f));
        histories.add(new History("789", "01/01/2021", "Test3", 25.50f));
        HistoryAdapter adapter = new HistoryAdapter(this, histories);
        history_list.setAdapter(adapter);
    }
}