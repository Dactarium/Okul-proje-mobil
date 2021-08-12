package com.eren.siparisuygulamasi.accessed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.BtnClickListener;
import com.eren.siparisuygulamasi.items.category.Category;
import com.eren.siparisuygulamasi.items.category.CategoryAdapter;
import com.eren.siparisuygulamasi.items.menu.Menu;
import com.eren.siparisuygulamasi.items.menu.MenuAdapter;
import com.eren.siparisuygulamasi.items.order.Order;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class InRestaurant extends AppCompatActivity {

    ListView category_list;
    ListView menu_list;

    ImageButton bill_button;
    ImageButton cart_button;

    TextView order_code_text;
    TextView cart_total_text;

    String rId;
    ArrayList<Order> cart;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_restaurant);

        database = FirebaseFirestore.getInstance();

        rId = getIntent().getStringExtra("RID");
        cart = new ArrayList<>();

        category_list = findViewById(R.id.list_category);
        menu_list = findViewById(R.id.list_menu);

        bill_button = (ImageButton) findViewById(R.id.button_bill);
        cart_button = (ImageButton) findViewById(R.id.button_cart);

        order_code_text = (TextView) findViewById(R.id.text_order_code);
        cart_total_text = (TextView) findViewById(R.id.text_cart_total);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String order_code = sharedPreferences.getString("ORDER_CODE","");

        order_code_text.setText(order_code);

        bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Orders.class));
            }
        });

        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                intent.putExtra("CART", cart);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Category> categories = new ArrayList<>();

        CollectionReference menu_ref = database.collection("restaurants").document(rId).collection("menu");

        menu_ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Category category = documentSnapshot.toObject(Category.class);
                    categories.add(category);
                }

                CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), categories);
                category_list.setAdapter(categoryAdapter);
            }
        });


        ArrayList<Menu> menus = new ArrayList<>();

        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                menus.clear();

                CollectionReference content_ref = menu_ref.document(categories.get(position).id).collection("content");

                content_ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Menu menu = documentSnapshot.toObject(Menu.class);
                            menus.add(menu);
                        }

                        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), menus, new BtnClickListener() {
                            @Override
                            public void addToCart(String name, float price) {
                                Boolean isExist = false;

                                for(Order cartItem : cart){
                                    if(cartItem.name.equals(name)){
                                        cartItem.increaseAmount();
                                        isExist = true;
                                    }
                                }

                                if(!isExist){
                                    cart.add(new Order(name, 1, price));
                                }

                                calculateCartTotal();
                            }
                        });
                        menu_list.setAdapter(menuAdapter);

                        category_list.setVisibility(View.GONE);
                        menu_list.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                cart = (ArrayList<Order>) data.getSerializableExtra("CART");
                calculateCartTotal();
            }
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
        if(category_list.getVisibility() == View.VISIBLE)
            super.onBackPressed();
        else{
            menu_list.setVisibility(View.GONE);
            category_list.setVisibility(View.VISIBLE);
        }
    }
}