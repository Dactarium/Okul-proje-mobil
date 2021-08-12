package com.eren.siparisuygulamasi.launch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eren.siparisuygulamasi.items.User;
import com.eren.siparisuygulamasi.ready.Mainmenu;
import com.eren.siparisuygulamasi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigninScreen extends AppCompatActivity {

    ConstraintLayout signin_layout;
    ConstraintLayout signup_layout;

    Button signin_button;
    Button signup_button;

    ImageButton show_password_button;

    TextView signin_info_text;
    TextView signin_title_text;
    TextView signup_text;
    TextView signup_mail_info_text;
    TextView signup_password_info_text;

    EditText signin_mail_input;
    EditText signin_password_input;
    EditText signup_name_input;
    EditText signup_surname_input;
    EditText signup_mail_input;
    EditText signup_password_input;

    FirebaseAuth mAuth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        signin_layout = (ConstraintLayout) findViewById(R.id.signin);
        signup_layout = (ConstraintLayout) findViewById(R.id.signup);

        signin_button = (Button) findViewById(R.id.button_signin);
        signup_button = (Button) findViewById(R.id.button_signup);
        show_password_button = (ImageButton) findViewById(R.id.button_show_password);

        signin_info_text = (TextView) findViewById(R.id.text_signin_info);
        signin_title_text = (TextView) findViewById(R.id.text_signin_title);
        signup_text = (TextView) findViewById(R.id.text_signup);
        signup_mail_info_text = (TextView) findViewById(R.id.text_signup_email_info);
        signup_password_info_text = (TextView) findViewById(R.id.text_signup_password_info);


        signin_mail_input = (EditText) findViewById(R.id.input_signin_email);
        signin_password_input = (EditText) findViewById(R.id.input_signin_password);
        signup_name_input = (EditText) findViewById(R.id.input_name);
        signup_surname_input = (EditText) findViewById(R.id.input_surname);
        signup_mail_input = (EditText) findViewById(R.id.input_signup_email);
        signup_password_input = (EditText) findViewById(R.id.input_signup_password);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin_info_text.setText("");
                signinUser();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });

        show_password_button.setOnClickListener(new View.OnClickListener() {
            Boolean isHide = false;
            @Override
            public void onClick(View v) {
                if(isHide){
                    signup_password_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHide = false;
                }
                else{
                    signup_password_input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHide = true;
                }

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

    private void signupUser(){
        String email = signup_mail_input.getText().toString();
        String name = signup_name_input.getText().toString();
        String surname = signup_surname_input.getText().toString();
        String password = signup_password_input.getText().toString();
        if(TextUtils.isEmpty(name)){
            signup_name_input.requestFocus();
        }else if(TextUtils.isEmpty(surname)){
            signup_surname_input.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            signup_mail_info_text.setText("*" + getString(R.string.mail_empty_warning));
            signup_mail_input.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            signup_password_info_text.setText("*" + getString(R.string.password_below_required));
            signup_password_input.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SigninScreen.this, getString(R.string.registered_successfully), Toast.LENGTH_SHORT).show();

                        createDoc(name, surname, email);
                        signin(email,password);

                    }else{
                        Toast.makeText(SigninScreen.this, getString(R.string.registration_error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void signinUser(){
        String email = signin_mail_input.getText().toString();
        String password = signin_password_input.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            signin_info_text.setText("*" + getString(R.string.fill_all_text_inputs));
            if(TextUtils.isEmpty(email)){
                signin_mail_input.requestFocus();
            }else{
                signin_password_input.requestFocus();
            }
        }else{
            DocumentReference docRef = database.collection("restaurants").document(email);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        signin_info_text.setText("*" + getString(R.string.user_is_manager));
                    }else{
                        signin(email,password);
                    }
                }
            });


        }
    }

    void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SigninScreen.this, getString(R.string.signin_successfull), Toast.LENGTH_SHORT).show();

                    DocumentReference customerRef = database.collection("customers").document(email);
                    customerRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);

                            SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                            SharedPreferences.Editor editPrefences = sharedPreferences.edit();

                            editPrefences.putString("EMAIL", email);
                            editPrefences.putString("NAME",user.name);
                            editPrefences.putString("SURNAME",user.surname);
                            editPrefences.commit();

                            startActivity(new Intent(getApplicationContext(), Mainmenu.class));
                            finish();;
                        }
                    });


                }else{
                    Toast.makeText(SigninScreen.this, getString(R.string.signin_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDoc(String name, String surname, String email){
        String uid = mAuth.getUid();

        CollectionReference customers = database.collection("customers");

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("surname", surname);
        data.put("email", email);
        data.put("uid", uid);

        customers.document(email).set(data);
    }



}