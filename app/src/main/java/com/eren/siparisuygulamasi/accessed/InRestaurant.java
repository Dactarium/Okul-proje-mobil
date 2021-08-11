package com.eren.siparisuygulamasi.accessed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.items.category.Category;
import com.eren.siparisuygulamasi.items.category.CategoryAdapter;
import com.eren.siparisuygulamasi.items.menu.Menu;
import com.eren.siparisuygulamasi.items.menu.MenuAdapter;

import java.util.ArrayList;

public class InRestaurant extends AppCompatActivity {

    ListView category_list;
    ListView menu_list;

    ImageButton bill_button;
    ImageButton cart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_restaurant);

        category_list = findViewById(R.id.list_category);
        menu_list = findViewById(R.id.list_menu);

        bill_button = (ImageButton) findViewById(R.id.button_bill);
        cart_button = (ImageButton) findViewById(R.id.button_cart);

        bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Orders.class));
            }
        });

        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });

        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        categories.add(new Category("Category"));
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        category_list.setAdapter(categoryAdapter);

        ArrayList<Menu> menus = new ArrayList<Menu>();
        menus.add(new Menu("Menu1", 15f));
        menus.add(new Menu("Menu2", 15.5f));
        menus.add(new Menu("Menu3", 1.99f));
        menus.add(new Menu("Menu4", 0.99f));
        menus.add(new Menu("Menu5", 13f));
        menus.add(new Menu("Menu2", 15.5f));
        menus.add(new Menu("Menu3", 1.99f));
        menus.add(new Menu("Menu4", 0.99f));
        menus.add(new Menu("Menu5", 13f));
        menus.add(new Menu("Menu2", 15.5f));
        menus.add(new Menu("Menu3", 1.99f));
        menus.add(new Menu("Menu4", 0.99f));
        menus.add(new Menu("Menu5", 13f));
        MenuAdapter menuAdapter = new MenuAdapter(this, menus);
        menu_list.setAdapter(menuAdapter);
    }
}