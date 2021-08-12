package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.order.Order;
import com.eren.siparisuygulamasi.items.order.OrderAdapter;
import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.DocumentTransform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Bill extends AppCompatActivity {

    ListView order_list;

    TextView total_text;
    TextView order_code_text;

    Button leave_button;

    ArrayList<Order> bill;
    String rId;

    FirebaseAuth mAuth;
    FirebaseFirestore database;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        bill = new ArrayList<>();

        order_list = (ListView) findViewById(R.id.list_current_orders);

        total_text = (TextView) findViewById(R.id.text_total);
        order_code_text = (TextView) findViewById(R.id.text_order_code);

        leave_button = (Button)findViewById(R.id.button_leave);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String order_code = sharedPreferences.getString("ORDER_CODE","");

        order_code_text.setText(order_code);

        leave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(Bill.this).create();
                alertDialog.setTitle(getString(R.string.bill_alert_box_title));
                alertDialog.setMessage(getString(R.string.bill_alert_box_msg));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                DocumentReference customerRef = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid());

                                Map<String, Object> customerData = new HashMap<>();

                                customerData.put("wantsBill", true);

                                customerRef.update(customerData);

                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("CURRENT_RESTAURANT_ACCESS_CODE", null);
                                editor.putString("CURRENT_RESTAURANT_ID", null);
                                editor.putBoolean("IS_ALLOWED", false);

                                editor.commit();

                                String email = sharedPreferences.getString("EMAIL", "");
                                String restaurant_name = sharedPreferences.getString("RESTAURANT_NAME", "");



                                timer.cancel();
                                finishAffinity();
                                startActivity(new Intent(getApplicationContext(), Mainmenu.class));
                                finish();

                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        rId = getIntent().getStringExtra("RID");
        bill = (ArrayList<Order>) getIntent().getSerializableExtra("BILL");

        calculateBillTotal();

        OrderAdapter adapter = new OrderAdapter(this, bill);
        order_list.setAdapter(adapter);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getBillData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        calculateBillTotal();
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }, 0, 5000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }

    void getBillData(){
        ArrayList<Order> tmp_bill = new ArrayList<>();

        CollectionReference orders_Ref = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid()).collection("bill");

        orders_Ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot doc: queryDocumentSnapshots){
                    Order order  = doc.toObject(Order.class);
                    tmp_bill.add(new Order(order.id, order.name, order.amount, order.price));
                }

                bill.clear();
                bill.addAll(tmp_bill);
            }
        });
    }

    void calculateBillTotal(){
        float total = 0;

        for(Order billItem: bill){
            total += billItem.getTotal();
        }

        total_text.setText(Float.toString(total));
    }
}