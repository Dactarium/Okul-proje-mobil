package com.eren.siparisuygulamasi.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.accessed.InRestaurant;
import com.eren.siparisuygulamasi.items.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class WaitForAllow extends AppCompatActivity {

    TextView order_code_text;

    String rId;

    FirebaseAuth mAuth;;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_access);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        rId = getIntent().getStringExtra("RID");

        order_code_text = (TextView) findViewById(R.id.text_order_code);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String order_code = sharedPreferences.getString("ORDER_CODE","");

        order_code_text.setText(order_code);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DocumentReference docRef = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);

                        if(user.isAllowed){
                            Intent intent = new Intent(getApplicationContext(), InRestaurant.class);
                            intent.putExtra("RID", rId);
                            startActivity(intent);
                            finish();
                            timer.cancel();
                        }

                    }
                });
            }
        }, 0, 5000);



    }
}