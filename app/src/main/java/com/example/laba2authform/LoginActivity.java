package com.example.laba2authform;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Log.d(TAG, " Started This Activity!");
        EditText username_field = (EditText) findViewById(R.id.username_login);
        EditText password_field = (EditText) findViewById(R.id.password_login);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button getmein_btn = (Button) findViewById(R.id.getmein_btn);
        databaseHelper = new DatabaseHelper(LoginActivity.this);

        getmein_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMeIn();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username = username_field.getText().toString().trim();
                String password = password_field.getText().toString().trim();

//                if(databaseHelper.login(username,password)){
//
//                   new Thread (() -> {
//                       Log.d(TAG, "THREAD");
//
//
//                       username_field.post(new Runnable() {
//                           @Override
//                           public void run() {
//                               username_field.setText("ASDADASDASDA");
//                           }
//                       });
//                       Log.d(TAG, username + "\t\t" + password);
//                   }).start();
//
//
//                }
//                else {
//                    toastMessage("Enter correct username and password!");
//                    username_field.setText("");
//                    password_field.setText("");
//                }

                new Thread(() -> {
                    Log.d(TAG, " statrted login thread");
                    if (databaseHelper.login(username, password)){

                        Log.d(TAG, "SUCSESSSSSSSS");
                        //
                    }else {
                        Log.d(TAG, "PIZDEC");
                    }
                }).start();







            }
        });

    }

    private void getMeIn(){
        toastMessage(TAG + " Getting you in");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void toastMessage(String message){
        Toast.makeText(this, message,  Toast.LENGTH_SHORT).show();
    }
}

