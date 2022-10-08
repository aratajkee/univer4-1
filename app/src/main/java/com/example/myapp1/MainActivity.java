package com.example.myapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    public int clickCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button timebtn = findViewById(R.id.time);
        ListView listView = findViewById(R.id.list);
        TextView textView = findViewById(R.id.textView1);

        ArrayList<String> listData = new ArrayList<>();
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setText("BUTTON WAS CLICKED!");
                clickCounter++;
                if(clickCounter > 5){
                    button1.setText("Хватит уже");
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCounter++;
                textView.setText("BUTTONS WAS CLICKED " + String.valueOf(clickCounter)+" TIMES");

            }
        });

        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
                Date date = new Date(System.currentTimeMillis());
                String tempdate = formatter.format(date);


                listData.add(String.valueOf(tempdate + "   Clicks done:  " + clickCounter));
                if(listData.size()>6){
                    //listData.clear();
                    listData.remove(0);
                }
                listView.setAdapter(adapter);
            }
        });
    }
}