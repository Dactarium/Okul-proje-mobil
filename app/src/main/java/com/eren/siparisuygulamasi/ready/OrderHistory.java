package com.eren.siparisuygulamasi.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.history.History;
import com.eren.siparisuygulamasi.items.history.HistoryAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class OrderHistory extends AppCompatActivity {

    ImageButton back_button;
    ListView history_list;

    ArrayList<History> histories;

    FirebaseAuth mAuth;
    FirebaseFirestore database;

    Timer timer;

    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        histories = new ArrayList<>();

        back_button = (ImageButton) findViewById(R.id.button_back);
        history_list = (ListView) findViewById(R.id.list_history);

        adapter = new HistoryAdapter(this, histories);
        history_list.setAdapter(adapter);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        getHistoryData();

    }

    void getHistoryData(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        CollectionReference historyRef = database.collection("customers").document(email).collection("history");

        historyRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                histories.clear();

                for(DocumentSnapshot history_doc : queryDocumentSnapshots){
                    histories.add(history_doc.toObject(History.class));
                }

                Collections.reverse(histories);
                adapter.notifyDataSetChanged();
            }
        });
    }
}