package com.example.laba2authform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    DatabaseHelper databaseHelper;
    UserList userArray = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, " Started This Activity!");

        Button buttonAdd = (Button) findViewById(R.id.button_add_user);
        Button triger_btn = (Button) findViewById(R.id.triger_btn);
        EditText userName = (EditText) findViewById(R.id.user_name);
        EditText passWord = (EditText) findViewById(R.id.user_pass);
        Button logout_btn = (Button) findViewById(R.id.logout_btn);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        final Looper looper = Looper.getMainLooper();
        final Handler handler = new Handler(looper) {
            public void handleMessage(Message message) {
                if ((boolean) message.obj) {
                    toastMessage("New user added!");
                } else {
                    toastMessage("Failed to add new user!");
                }
            }
        };

        buttonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString().trim();
                String password = passWord.getText().toString().trim();


                new Thread(() -> {
                    Log.d(TAG, " started adding user thread");
                    if (username.length() != 0 && password.length() != 0) {
                        Looper.prepare();
                        databaseHelper.addData(username, password);

                        userName.post(new Runnable() {
                            @Override
                            public void run() {
                                userName.setText("");
                            }
                        });

                        passWord.post(new Runnable() {
                            @Override
                            public void run() {
                                passWord.setText("");
                            }
                        });

                        final Message message = Message.obtain();
                        message.obj = true;
                        handler.sendMessage(message);
                    } else {
                        final Message message = Message.obtain();
                        message.obj = false;
                        handler.sendMessage(message);
                    }

                }).start();

            }
        });

        triger_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrigerPause.class);
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

    private void logout() {
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
        Log.d(TAG, " STOPED THIS ACTIVITY!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, " THIS ACTIVITY RESTARTED!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " DESTROYED THIS ACTIVITY!");
    }

    public void goUserList(View view) {
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


}