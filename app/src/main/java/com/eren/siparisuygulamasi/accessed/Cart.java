package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.order.CartAdapter;
import com.eren.siparisuygulamasi.items.order.CartButtonListener;
import com.eren.siparisuygulamasi.items.order.Order;
import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {

    ListView cartOrder_list;

    TextView order_code_text;
    TextView cart_total_text;

    Button order_button;

    ArrayList<Order> cart;

    FirebaseFirestore database;
    FirebaseAuth mAuth;

    String rId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        cart = (ArrayList<Order>) getIntent().getSerializableExtra("CART");
        rId = getIntent().getStringExtra("RID");

        cartOrder_list = (ListView) findViewById(R.id.list_cart_orders);

        order_code_text = (TextView) findViewById(R.id.text_order_code);
        cart_total_text = (TextView) findViewById(R.id.text_cart_total);

        order_button = (Button) findViewById(R.id.button_order);

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(Cart.this).create();
                alertDialog.setTitle(getString(R.string.order_alert_box_title));
                alertDialog.setMessage(getString(R.string.order_alert_box_msg));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                addToOrder();

                                Intent intent = new Intent();
                                intent.putExtra("CART", new ArrayList<Order>());
                                setResult(Activity.RESULT_OK, intent);
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

        CartAdapter adapter = new CartAdapter(this, cart);
        adapter.setCartButtonListener( new CartButtonListener() {
            @Override
            public void changeAmount(int position, Button button) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), button);

                for(int i = 1; i <= cart.get(position).getAmount(); i++){
                    popupMenu.getMenu().add(Integer.toString(i));
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        cart.get(position).setAmount(Integer.parseInt(item.getTitle().toString()));
                        calculateCartTotal();
                        adapter.notifyDataSetChanged();

                        return true;
                    }
                });

                popupMenu.show();
            }

            @Override
            public void removeFromCart(int position) {
                cart.remove(position);
                calculateCartTotal();
                adapter.notifyDataSetChanged();
            }
        });
        cartOrder_list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String order_code = sharedPreferences.getString("ORDER_CODE", "");

        order_code_text.setText(order_code);

        calculateCartTotal();
    }
    void addToOrder(){

        CollectionReference orders_Ref = database.collection("restaurants").document(rId).collection("customers").document(mAuth.getUid()).collection("orders");

        for(Order cart_item : cart){

            orders_Ref.whereEqualTo("name",cart_item.name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty()){
                        DocumentReference order_ref = orders_Ref.document();

                        Map<String, Object> data = new HashMap<>();

                        data.put("id", order_ref.getId());
                        data.put("name", cart_item.name);
                        data.put("amount", cart_item.amount);
                        data.put("price", cart_item.price);

                        order_ref.set(data);
                    }else{
                        Order order = queryDocumentSnapshots.toObjects(Order.class).get(0);

                        order.increaseAmount(cart_item.amount);

                        DocumentReference order_ref = orders_Ref.document(order.getId());

                        Map<String, Object> data = new HashMap<>();

                        data.put("id", order_ref.getId());
                        data.put("name", order.name);
                        data.put("amount", order.amount);
                        data.put("price", order.price);

                        order_ref.set(data);
                    }
                }
            });

        }

    }

    void calculateCartTotal(){
        float total = 0;

        for(Order cartItem: cart){
            total += cartItem.getTotal();
        }

        cart_total_text.setText(Float.toString(total));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("CART", cart);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}