package com.eren.siparisuygulamasi.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eren.siparisuygulamasi.R;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        finish();
    }
}