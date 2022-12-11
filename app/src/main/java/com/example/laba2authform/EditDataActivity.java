package com.example.laba2authform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    private static  final String TAG = "EditDataActivity";
    private Button delete_button,save_button;
    private EditText editable_item;
    private EditText editable_item_pass;
    DatabaseHelper databaseHelper;

    private String selectedItem;
    private String selectedPass;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        Log.d(TAG, " STARTED THIS ACTIVITY");
        delete_button = (Button) findViewById(R.id.delete_button);
        save_button = (Button) findViewById(R.id.save_button);
        editable_item = (EditText) findViewById(R.id.editable_item);
        editable_item_pass = (EditText) findViewById(R.id.editable_item_pass);
        databaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedItem = receivedIntent.getStringExtra("item");
        selectedPass = receivedIntent.getStringExtra("pass");
        editable_item.setText(selectedItem);
        editable_item_pass.setText(selectedPass);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String item = editable_item.getText().toString();
                String pass = editable_item_pass.getText().toString();
                if (!item.equals("") &&!(pass.equals(""))){
                databaseHelper.updateItem(item,pass, selectedID, selectedItem, selectedPass);

                }else{
                    toastMessage("Введите username");
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteItem(selectedID,selectedItem);
                editable_item.setText("");
                editable_item_pass.setText("");
                toastMessage("deleted from database");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " Started This Activity!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG," PAUSED THIS ACTIVITY");
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
    public void goBack(View view){
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
