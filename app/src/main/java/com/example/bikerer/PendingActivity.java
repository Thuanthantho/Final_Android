package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class PendingActivity extends AppCompatActivity {
    DatabaseReference tripDbRef;
    private FirebaseAuth mAuth;
    String id,userEmail,destination, distance, vehicle,price, driver, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String UserEmail = currentUser.getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tripDbRef = FirebaseDatabase.getInstance().getReference("Trips-pending");

        tripDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripDatasnap : dataSnapshot.getChildren()){
                    Trip trips = tripDatasnap.getValue(Trip.class);
                    if(Objects.equals(trips.userEmail, UserEmail) && Objects.equals(trips.status, "Pending")){
                        id= trips.id;
                        userEmail= trips.userEmail;
                        destination= trips.destination;
                        distance = trips.distance;
                        vehicle = trips.vehicle;
                        price = trips.price;
                        driver = trips.driver;
                        status = trips.status;
                    }else{
                        Toast.makeText(PendingActivity.this,"Driver Found!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PendingActivity.this, HomeActivity.class);
                        intent.putExtra("userEmail", UserEmail);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}