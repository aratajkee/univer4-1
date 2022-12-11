package com.example.laba2authform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {

    private  final String TAG = "MainActivity";
    DatabaseHelper databaseHelper;
    UserList userArray = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, " Started This Activity!");
        Button buttonAdd =(Button) findViewById(R.id.button_add_user);
        Button triger_btn = (Button) findViewById(R.id.triger_btn);
        EditText userName = (EditText) findViewById(R.id.user_name);
        EditText passWord = (EditText) findViewById(R.id.user_pass);
        Button logout_btn = (Button) findViewById(R.id.logout_btn);
        databaseHelper = new DatabaseHelper(MainActivity.this);

        buttonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(userName.getText().toString());
                user.setPassword(passWord.getText().toString());
                userArray.addUser(user.getUsername());

                if(userName.length() != 0){
                   databaseHelper.addData(userName.getText().toString().trim(), passWord.getText().toString().trim());
                    userName.setText("");
                    passWord.setText("");
                }else{
                    toastMessage("Введите данные пользователя!");
                }


            }
        });

        triger_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TrigerPause.class);
                startActivity(intent);
            }
        });

        logout_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }
    private void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " Started This Activity!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " PAUSED THIS ACTIVITY!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, " RESUMED THIS ACTIVITY!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG," STOPED THIS ACTIVITY!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, " THIS ACTIVITY RESTARTED!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG," DESTROYED THIS ACTIVITY!");
    }

    public void goUserList(View view){
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }



}