package com.eren.siparisuygulamasi.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.eren.siparisuygulamasi.R;
import com.eren.siparisuygulamasi.accessed.InRestaurant;
import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.eren.siparisuygulamasi.ready.WaitForAllow;
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
                        Intent intent;
                        if(user == null){
                            intent = new Intent(getApplicationContext(), SigninScreen.class);
                        }else{

                            if(getLocalUserData("CURRENT_RESTAURANT_ACCESS_CODE") == null ){
                                intent = new Intent(getApplicationContext(), Mainmenu.class);
                            }else{
                                if(!getLocalUserDataBoolean("IS_ALLOWED")){
                                    intent = new Intent(getApplicationContext(), WaitForAllow.class);
                                }else{
                                    intent = new Intent(getApplicationContext(), InRestaurant.class);
                                }
                                intent.putExtra("RID", getLocalUserData("CURRENT_RESTAURANT_ID"));
                            }
                        }

                        startActivity(intent);
                        finish();
                        timer.cancel();
                    }
                });
            }
        }, 750);


    }

    String getLocalUserData(String dataName){
        String localUserData;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        localUserData = sharedPreferences.getString(dataName, null);

        return localUserData;
    }

    Boolean getLocalUserDataBoolean(String dataName){
        Boolean localUserData;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        localUserData = sharedPreferences.getBoolean(dataName, false);

        return localUserData;
    }

}