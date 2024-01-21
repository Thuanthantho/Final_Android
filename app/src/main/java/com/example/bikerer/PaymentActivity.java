package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;
import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {
    String distance;
    String status = "Pending";
    BigDecimal rent;
    String destinationName;
    String vehicle;
    DatabaseReference tripDbRef;
    DatabaseReference driverDbRef;
    String latitude;
    String longitude;
    String closestDriverName = "";

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
        if(bundle!=null){
            latitude=bundle.getString("latitude");
        }
        if(bundle!=null){
            longitude=bundle.getString("longitude");
        }
            if(Objects.equals(vehicle, "car")){
                updateVisibility(R.id.carLayout,true);
                updateVisibility(R.id.motorbikeLayout,false);
                TextView rentPrice= findViewById(R.id.rentPrice);
                rent = new BigDecimal(distance).multiply(new BigDecimal("0.6"));
                String rentText = String.valueOf(rent);
                rentPrice.setText(rentText+" $");
            }
            if(Objects.equals(vehicle, "motorbike")) {
                updateVisibility(R.id.motorbikeLayout, true);
                updateVisibility(R.id.carLayout, false);
                TextView rentPrice = findViewById(R.id.rentPrice);
                rent = new BigDecimal(distance).multiply(new BigDecimal("0.4"));
                String rentText = String.valueOf(rent);
                rentPrice.setText(rentText+" $");
            }
            TextView distanceView = findViewById(R.id.distancePayment);
            distanceView.setText(distance+ " km");
            TextView vat = findViewById(R.id.VAT);
            String vatPrice= String.valueOf(rent.multiply(new BigDecimal("0.05")));
            vat.setText(vatPrice+" $");

            TextView totalPriceView = findViewById(R.id.totalPrice);
            String totalPrice= String.valueOf(rent.multiply(new BigDecimal("1.05")));
            totalPriceView.setText(totalPrice+" $");

            TextView name=findViewById(R.id.destinationName);
            name.setText(destinationName);
            tripDbRef = FirebaseDatabase.getInstance().getReference("Trips-pending");
            Button confirmPaymentButton=findViewById(R.id.confirmPaymentButton);
            confirmPaymentButton.setOnClickListener(v->insertTripData());
            TextView driverTextView = findViewById(R.id.driver);
            driverDbRef = FirebaseDatabase.getInstance().getReference("drivers");
            driverDbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    float[] shortestCarDistance = new float[1];
                    float[] shortestMotorbikeDistance = new float[1];
                    int maxNumTrips = Integer.MAX_VALUE;
                    shortestCarDistance[0] = Float.POSITIVE_INFINITY;
                    shortestMotorbikeDistance[0] = Float.POSITIVE_INFINITY;
                    for (DataSnapshot driverDataSnapshot : dataSnapshot.getChildren()) {
                        Driver driver = driverDataSnapshot.getValue(Driver.class);
                        assert driver != null;
                        if (Objects.equals(vehicle, "car")) {
                            if (Objects.equals(driver.getVehicle(), "car")) {
//                                Location.distanceBetween(Double.parseDouble(latitude),Double.parseDouble(longitude), Double.parseDouble(driver.getLatitude()), Double.parseDouble(driver.getLongitude()), distance);
//                                Toast.makeText(PaymentActivity.this, String.valueOf(distance[0] / 1000.0f), Toast.LENGTH_LONG).show();
//                                if(distance[0]<shortestCarDistance[0]){
//                                    shortestCarDistance[0]=distance[0];
//                                    closestDriverName = driver.name;
//                                }
                                float[] distance = new float[1];
                                Location.distanceBetween(Double.parseDouble(latitude),Double.parseDouble(longitude), Double.parseDouble(driver.getLatitude()), Double.parseDouble(driver.getLongitude()), distance);
                                if (distance[0] < shortestCarDistance[0]) {
                                    shortestCarDistance[0] = distance[0];
                                    closestDriverName = driver.getName();
                                    maxNumTrips = Integer.parseInt(driver.getNumTrips());
                                } else if (distance[0] == shortestCarDistance[0] && Integer.parseInt(driver.getNumTrips()) > maxNumTrips) {
                                    // Nếu có nhiều driver có cùng shortestDistance, chọn driver có numTrips nhỏ nhất
                                    maxNumTrips = Integer.parseInt(driver.getNumTrips());
                                    closestDriverName = driver.getName();
                                }
                            }

                        }
//                        if(Objects.equals(vehicle, "motorbike")){
//                            if ( Objects.equals(driver.getVehicle(), "motorbike")) {
//                                Location.distanceBetween(Double.parseDouble(latitude),Double.parseDouble(longitude), Double.parseDouble(driver.getLatitude()), Double.parseDouble(driver.getLongitude()), distance);
//                                Toast.makeText(PaymentActivity.this, String.valueOf(distance[0] / 1000.0f), Toast.LENGTH_LONG).show();
//                                if(distance[0]<shortestMotorbikeDistance[0]){
//                                    shortestMotorbikeDistance[0]=distance[0];
//                                    closestDriverName = driver.name;
//                                }
//                            }
//
//                        }
                        if (Objects.equals(vehicle, "motorbike")) {
                            if (Objects.equals(driver.getVehicle(), "motorbike")) {
                                float[] distance = new float[1];
                                Location.distanceBetween(Double.parseDouble(latitude),Double.parseDouble(longitude), Double.parseDouble(driver.getLatitude()), Double.parseDouble(driver.getLongitude()), distance);
                                if (distance[0] < shortestMotorbikeDistance[0]) {
                                    shortestMotorbikeDistance[0] = distance[0];
                                    closestDriverName = driver.getName();
                                    maxNumTrips = Integer.parseInt(driver.getNumTrips());
                                } else if (distance[0] == shortestMotorbikeDistance[0] && Integer.parseInt(driver.getNumTrips()) > maxNumTrips) {
                                    // Nếu có nhiều driver có cùng shortestDistance, chọn driver có numTrips nhỏ nhất
                                    maxNumTrips = Integer.parseInt(driver.getNumTrips());
                                    closestDriverName = driver.getName();
                                }
                            }
                        }
                    }
                    driverTextView.setText("Driver: " + closestDriverName);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }
    private void insertTripData(){
        // Fetch user information
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = currentUser.getEmail(); // Get the user's email
        String id = tripDbRef.push().getKey();
        Trip trip = new Trip(id,userEmail,destinationName, distance, vehicle, String.valueOf(rent.multiply(new BigDecimal("1.05"))), closestDriverName, status);
        assert id != null;
        tripDbRef.child(id).setValue(trip);
        Toast.makeText(PaymentActivity.this,"Finding Drivers... Please Wait!",Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(PaymentActivity.this, PendingActivity.class);
        intent1.putExtra("userEmail", userEmail);
        //intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }
    private void updateVisibility(int ID, boolean visible) {
        View view = findViewById(ID);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}