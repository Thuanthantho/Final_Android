package com.example.bikerer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDeviceActivity extends AppCompatActivity {
    String distance;
    String destinationName;
    String latitude;
    String longitude;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
             distance= bundle.getString("distance");
        }
        if (bundle != null) {
            destinationName=bundle.getString("destinationName");
        }
        if(bundle!=null){
            latitude=bundle.getString("latitude");
        }
        if(bundle!=null){
            longitude=bundle.getString("longitude");
        }
        ImageView carView = findViewById(R.id.Car);
        carView.setOnClickListener(v->{
            Intent intent1 = new Intent(SelectDeviceActivity.this, PaymentActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("vehicle", "car");
            bundle2.putString("distance", distance);
            bundle2.putString("destinationName",destinationName);
            bundle2.putString("latitude",latitude);
            bundle2.putString("longitude",longitude);
            intent1.putExtras(bundle2);
            startActivity(intent1);
        });
        ImageView motorbikeView = findViewById(R.id.Motorbike);
        motorbikeView.setOnClickListener(v->{
            Intent intent1 = new Intent(SelectDeviceActivity.this, PaymentActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("vehicle", "motorbike");
            bundle2.putString("distance",distance);
            bundle2.putString("destinationName",destinationName);
            bundle2.putString("latitude",latitude);
            bundle2.putString("longitude",longitude);
            intent1.putExtras(bundle2);
            startActivity(intent1);
        });
    }
}