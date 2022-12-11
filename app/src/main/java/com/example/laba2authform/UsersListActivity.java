package com.example.laba2authform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Log.d(TAG, " Started this Activity");
        listView = findViewById(R.id.userlist);
        databaseHelper = new DatabaseHelper(this);
        populateListView();
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

    private void populateListView() {

        Cursor data = databaseHelper.getData();
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
                String pass = "ERROR";
                Log.d(TAG," onItemClick: You Clicked On "+ item);

                Cursor data = databaseHelper.getItemId(item);
                int itemId = -1;
                while (data.moveToNext()){
                    itemId = data.getInt(0);
                }
                if(itemId>-1){
                    Log.d(TAG, " onItemClick: The ID Is: "+itemId);
                    Intent editScreenIntent = new Intent(UsersListActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemId);
                    editScreenIntent.putExtra("item",item);

                    Cursor passdata = databaseHelper.getPass(String.valueOf(itemId),item);
                    while (passdata.moveToNext()){
                        pass = passdata.getString(0);
                    }
                    editScreenIntent.putExtra("pass",pass);
                    startActivity(editScreenIntent);
                }else{
                    toastMessage("No ID For This Name!");
                }
            }
        });
    }


    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}