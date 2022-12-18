package com.example.laba2authform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laba2authform.UserList;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private ListView listView;
    private static final String TAG = "UserListActivity";
    Looper looper;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Log.d(TAG, " Started this Activity");

        listView = findViewById(R.id.userlist);

        databaseHelper = new DatabaseHelper(this);
        looper = Looper.getMainLooper();
        final Handler handler = new Handler(looper) {
            public void handleMessage(Message message) {

                data = (Cursor) message.obj;
                populateListView();
            }

        };

        new Thread(() -> {

            Log.d(TAG, "Started thread to get data from database");
            final Message message = Message.obtain();
            message.obj = databaseHelper.getData();
            handler.sendMessage(message);

        }).start();


    }

    private void populateListView() {

        //Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, " onItemClick: You Clicked On " + item);

                //Cursor data = databaseHelper.getItemId(item);

                final Handler handler = new Handler(looper) {
                    public void handleMessage(Message message) {

                        data = (Cursor) message.obj;
                        int itemId = -1;
                        while (data.moveToNext()) {
                            itemId = data.getInt(0);
                        }
                        if (itemId > -1) {
                            Log.d(TAG, " onItemClick: The ID Is: " + itemId);
                            Intent editScreenIntent = new Intent(UsersListActivity.this, EditDataActivity.class);
                            editScreenIntent.putExtra("id", itemId);
                            editScreenIntent.putExtra("item", item);

                            Cursor passData = databaseHelper.getPass(String.valueOf(itemId), item);
                            String pass = "ERROR";
                            while (passData.moveToNext()) {
                                pass = passData.getString(0);
                            }
                            editScreenIntent.putExtra("pass", pass);
                            startActivity(editScreenIntent);
                        } else {
                            toastMessage("No ID For This Name!");
                        }
                    }

                };

                new Thread(() -> {

                    Log.d(TAG, "Started thread to get item id from database");
                    final Message message = Message.obtain();
                    message.obj = databaseHelper.getItemId(item);
                    handler.sendMessage(message);

                }).start();


            }
        });
    }


    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " Started This Activity!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " Paused this Activity");
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
}