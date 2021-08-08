package com.eren.siparisuygulamasi.launch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.eren.siparisuygulamasi.R;

public class LoginScreen extends AppCompatActivity {

    ConstraintLayout signin_layout;
    ConstraintLayout signup_layout;

    Button signin_button;

    TextView signup_text;
    TextView signin_title_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        signin_layout = (ConstraintLayout) findViewById(R.id.signin);
        signup_layout = (ConstraintLayout) findViewById(R.id.signup);

        signin_button = (Button) findViewById(R.id.button_signin);

        signup_text = (TextView) findViewById(R.id.text_signup);
        signin_title_text = (TextView) findViewById(R.id.text_signin_title);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Mainmenu.class));
            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin_layout.setVisibility(View.INVISIBLE);
                signup_layout.setVisibility(View.VISIBLE);
            }
        });

        signin_title_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_layout.setVisibility(View.INVISIBLE);
                signin_layout.setVisibility(View.VISIBLE);
            }
        });
    }
}