package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DriverProfileActivity extends AppCompatActivity {
    DatabaseReference driverDbRef;
    String currentDriverName;
    Button getRequestBtn, logout;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        Intent intent = getIntent();
        String driverEmail = intent.getStringExtra("driverEmail");
        getRequestBtn = findViewById(R.id.getRequestBtn);
        logout = findViewById(R.id.logoutBtn2);

        driverDbRef = FirebaseDatabase.getInstance().getReference("drivers");
        driverDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot driverDatasnap : snapshot.getChildren()){
                    Driver currentDriver = driverDatasnap.getValue(Driver.class);
                    if(Objects.equals(currentDriver.userEmail, driverEmail)) {
                        currentDriverName = currentDriver.name;
                        TextView driverName = findViewById(R.id.driverName);
                        driverName.setText(currentDriverName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        TextView driverEmailTv = findViewById(R.id.driverEmail);
        driverEmailTv.setText(driverEmail);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetTripRequestActivity.class);
                intent.putExtra("driverName", currentDriverName);
                startActivity(intent);
            }
        });
    }
}