package com.example.bikerer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    DatabaseReference driverDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button addDriver= findViewById(R.id.addDriver);
        addDriver.setOnClickListener(v->{
            // Khởi tạo driverDbRef
            driverDbRef = FirebaseDatabase.getInstance().getReference().child("drivers");
            String id = driverDbRef.push().getKey();
//            Driver trip = new Driver(id,"driver1@gmail.com","Hoang Nguyen", "40", "car", "10.732688351339705","106.69775689472345");
//            Driver trip = new Driver(id,"driver2@gmail.com","Tung Dang", "12", "car", "10.727709893580675","106.69694350373761");
//            Driver trip = new Driver(id,"driver3@gmail.com","Hung Ngo", "23", "motorbike", "10.729236","106.698181");
//            Driver trip = new Driver(id,"driver4@gmail.com","Huy Ly", "8", "motorbike", "10.735636","106.689052");
            Driver trip = new Driver(id,"driver5@gmail.com","Hao Tran", "25", "motorbike", "10.729236","106.698181");
//            Driver trip = new Driver(id,"driver6@gmail.com","Hieu Nguyen", "13", "car", "10.727709893580675","106.69694350373761");

            assert id != null;
            driverDbRef.child(id).setValue(trip);
        });



    }
}
