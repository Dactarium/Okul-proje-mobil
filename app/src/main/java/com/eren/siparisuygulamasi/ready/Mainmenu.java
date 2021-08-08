package com.eren.siparisuygulamasi.ready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.accessed.CurrentOrders;

public class Mainmenu extends AppCompatActivity {

    ImageButton menu_button;
    ImageButton scan_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        menu_button = (ImageButton) findViewById(R.id.button_menu);
        scan_button = (ImageButton) findViewById(R.id.button_scan);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), menu_button);

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.settings:
                                break;
                            case R.id.history:
                                startActivity(new Intent(getApplicationContext(), OrderHistory.class));
                                break;
                            case R.id.signout:
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

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CurrentOrders.class));
            }
        });
    }
}