package com.example.bikerer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {
    String distance;
    BigDecimal rent;
    String destinationName;
    String vehicle;
    DatabaseReference tripDbRef;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            vehicle = (String) bundle.get("vehicle");
        } else {
            vehicle = null;
        }
        if(bundle!=null){
            distance= bundle.getString("distance");
        }
        if(bundle!=null){
            destinationName=bundle.getString("destinationName");
        }
            if(Objects.equals(vehicle, "car")){
                updateVisibility(R.id.carLayout,true);
                updateVisibility(R.id.motorbikeLayout,false);
                TextView rentPrice= findViewById(R.id.rentPrice);
                rent = new BigDecimal(distance).multiply(new BigDecimal("0.6"));
                rentPrice.setText(String.valueOf(rent));
            }
            if(Objects.equals(vehicle, "motorbike")) {
                updateVisibility(R.id.motorbikeLayout, true);
                updateVisibility(R.id.carLayout, false);
                TextView rentPrice = findViewById(R.id.rentPrice);
                rent = new BigDecimal(distance).multiply(new BigDecimal("0.4"));
                rentPrice.setText(String.valueOf(rent));
            }
            TextView vat = findViewById(R.id.VAT);
            vat.setText(String.valueOf(rent.multiply(new BigDecimal("0.05"))));

            TextView totalPrice = findViewById(R.id.totalPrice);
            totalPrice.setText(String.valueOf(rent.multiply(new BigDecimal("1.05"))));

            TextView name=findViewById(R.id.destinationName);
            name.setText(destinationName);
            tripDbRef = FirebaseDatabase.getInstance().getReference("Trips");
            Button confirmPaymentButton=findViewById(R.id.confirmPaymentButton);
            confirmPaymentButton.setOnClickListener(v->insertTripData());
    }
    private void insertTripData(){
        // Fetch user information
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = currentUser.getEmail(); // Get the user's email
        String id = tripDbRef.push().getKey();
        Trip trip = new Trip(id,userEmail,destinationName, distance, vehicle, String.valueOf(rent.multiply(new BigDecimal("1.05"))));
        assert id != null;
        tripDbRef.child(id).setValue(trip);
        Toast.makeText(PaymentActivity.this,"Confirm payment successfully!",Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(PaymentActivity.this, HomeActivity.class);
        intent1.putExtra("userEmail", userEmail);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }
    private void updateVisibility(int ID, boolean visible) {
        View view = findViewById(ID);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}