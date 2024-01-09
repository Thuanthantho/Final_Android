package com.example.bikerer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SelectDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        ImageView carView = findViewById(R.id.Car);
        carView.setOnClickListener(v->{
            Intent intent1 = new Intent(SelectDeviceActivity.this, PaymentActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        });
        ImageView motorbikeView = findViewById(R.id.Motorbike);
        motorbikeView.setOnClickListener(v->{
            Intent intent1 = new Intent(SelectDeviceActivity.this, PaymentActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        });
    }
}