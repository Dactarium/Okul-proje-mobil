package com.eren.siparisuygulamasi.ready;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.accessed.InRestaurant;
import com.eren.siparisuygulamasi.items.Restaurant;
import com.eren.siparisuygulamasi.items.User;
import com.eren.siparisuygulamasi.launch.SigninScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.UnknownServiceException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Mainmenu extends AppCompatActivity {

    ImageButton menu_button;

    TextView username_text;

    EditText access_code_input;

    FirebaseAuth mAuth;
    FirebaseFirestore database;

    String previous_access_code = "";
    String username;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        menu_button = (ImageButton) findViewById(R.id.button_menu);

        username_text = (TextView) findViewById(R.id.text_username);

        access_code_input = (EditText) findViewById(R.id.input_access_code);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), menu_button);

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.history:
                                startActivity(new Intent(getApplicationContext(), OrderHistory.class));
                                break;
                            case R.id.signout:
                                mAuth.signOut();
                                SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(getApplicationContext(), SigninScreen.class));
                                finish();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String access_code = access_code_input.getText().toString().toUpperCase();

                if(access_code.length() > 4 && access_code.length() < 7 && !access_code.equals(previous_access_code)){
                    previous_access_code = access_code;
                    CollectionReference restaurantsRef =  database.collection("restaurants");
                    restaurantsRef.whereEqualTo("access_code", access_code).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty()){
                                System.out.println("onSuccess: List Empty");
                            }else{
                                Restaurant restaurant = queryDocumentSnapshots.toObjects(Restaurant.class).get(0);

                                addRestaurantQueue(restaurant.mail, restaurant.restaurant_name, access_code);

                            }

                        }
                    });
                }

            }
        }, 0, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();

        username = getLocalUserData("NAME") + " " + getLocalUserData("SURNAME");
        username_text.setText(username);
    }

    void addRestaurantQueue(String restaurant_id, String restaurant_name, String access_code){
        String uid = mAuth.getUid();
        CollectionReference customers = database.collection("restaurants").document(restaurant_id).collection("customers");

        customers.whereEqualTo("id", uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){

                    String orderCode = generateOrderCode();

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("ORDER_CODE", orderCode);
                    editor.putString("RESTAURANT_NAME",restaurant_name);

                    editor.commit();

                    Map<String, Object> data = new HashMap<>();

                    data.put("id", uid);
                    data.put("isAllowed",false);
                    data.put("wantsBill",false);
                    data.put("name", username);
                    data.put("order_code",orderCode);
                    data.put("status","Onay bekliyor");
                    data.put("note","");

                    customers.document(uid).set(data);

                }else{
                    User user = queryDocumentSnapshots.toObjects(User.class).get(0);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("ORDER_CODE", user.order_code);
                    editor.putString("RESTAURANT_NAME",restaurant_name);

                    editor.commit();

                    Map<String, Object> data = new HashMap<>();

                    data.put("wantsBill",false);

                    customers.document(uid).update(data);
                }

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("CURRENT_RESTAURANT_ACCESS_CODE", access_code);
                editor.putString("CURRENT_RESTAURANT_ID", restaurant_id);
                editor.putBoolean("IS_ALLOWED", false);

                editor.commit();

                Intent intent = new Intent(getApplicationContext(), WaitForAllow.class);
                intent.putExtra("RID", restaurant_id);
                startActivity(intent);
                finish();
                timer.cancel();
            }
        });


    }

    String getLocalUserData(String dataName){
        String localUserData;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        localUserData = sharedPreferences.getString(dataName, "");

        return localUserData;
    }


    String generateOrderCode(){
        String orderCode = username.substring(0,1);
        LocalDateTime date = LocalDateTime.now();
        int seconds = date.toLocalTime().toSecondOfDay();
        orderCode = orderCode + Integer.toString(seconds,18) + Integer.toString(ThreadLocalRandom.current().nextInt(0, 18),18);
        return orderCode.toUpperCase();
    }
}