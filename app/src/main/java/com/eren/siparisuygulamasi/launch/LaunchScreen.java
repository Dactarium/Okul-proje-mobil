package com.eren.siparisuygulamasi.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(user == null){
                            startActivity(new Intent(getApplicationContext(), SigninScreen.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), Mainmenu.class));
                        }
                        finish();
                        timer.cancel();
                    }
                });
            }
        }, 750);


    }
}