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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Timer;
import java.util.TimerTask;

public class WaitForAllow extends AppCompatActivity {

    TextView order_code_text;

    String rId;

    FirebaseAuth mAuth;;
    FirebaseFirestore database;

    Timer timer;

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

        DocumentReference docRef = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);

                            if (user.isAllowed) {

                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean("IS_ALLOWED", true);

                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), InRestaurant.class);
                                intent.putExtra("RID", rId);
                                startActivity(intent);
                                finish();
                                timer.cancel();
                            }
                        }else{
                            onBackPressed();
                        }
                    }
                });
            }
        }, 0, 5000);
    }


    @Override
    public void onBackPressed() {
        timer.cancel();

        DocumentReference docRef = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid());

        docRef.delete();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("CURRENT_RESTAURANT_ACCESS_CODE", null);
        editor.putString("CURRENT_RESTAURANT_ID", null);
        editor.putBoolean("IS_ALLOWED", false);

        editor.commit();

        startActivity(new Intent(getApplicationContext(), Mainmenu.class));
        finish();
    }
}