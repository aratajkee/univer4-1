package com.example.laba2authform;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";
    private Button delete_button, save_button;
    private EditText editable_item;
    private EditText editable_item_pass;
    DatabaseHelper databaseHelper;

    private String selectedItem;
    private String selectedPass;
    private int selectedID;

    private final Looper looper = Looper.getMainLooper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
                final Handler handler = new Handler(looper) {
                    @Override
                    public void handleMessage(Message updateMessage) {
                        if ((Boolean) updateMessage.obj) {
                            toastMessage(selectedItem + " updated!");
                        } else {
                            toastMessage("Something went wrong!");
                        }
                    }
                };

                String item = editable_item.getText().toString();
                String pass = editable_item_pass.getText().toString();

                if (!item.equals("") && !(pass.equals(""))) {

                    new Thread(() -> {
                        final Message updateMessage = Message.obtain();
                        updateMessage.obj = databaseHelper.updateItem(item, pass, selectedID, selectedItem, selectedPass);
                        handler.sendMessage(updateMessage);
                    }).start();
                    //databaseHelper.updateItem(item,pass, selectedID, selectedItem, selectedPass);
                } else {
                    toastMessage("Введите username и password!");
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                databaseHelper.deleteItem(selectedID,selectedItem);
//                editable_item.setText("");
//                editable_item_pass.setText("");
//                toastMessage("deleted from database");
//

                final Handler handler = new Handler(looper) {
                    @Override
                    public void handleMessage(Message deleteMessage) {
                        if ((Boolean) deleteMessage.obj) {
                            toastMessage(selectedItem + " deleted from database!");
                        } else {
                            toastMessage("Something went wrong!");
                        }
                    }
                };

                new Thread(() -> {
                    final Message deleteMessage = Message.obtain();
                    deleteMessage.obj = databaseHelper.deleteItem(selectedID, selectedItem);
                    handler.sendMessage(deleteMessage);

                    editable_item.post(new Runnable() {
                        @Override
                        public void run() {
                            editable_item.setText("");
                        }
                    });
                    editable_item_pass.post(new Runnable() {
                        @Override
                        public void run() {
                            editable_item_pass.setText("");
                        }
                    });

                }).start();


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
        Log.d(TAG, " PAUSED THIS ACTIVITY");
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

    public void goBack(View view) {
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
