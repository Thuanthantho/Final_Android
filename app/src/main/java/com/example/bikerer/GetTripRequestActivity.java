package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetTripRequestActivity extends AppCompatActivity {
    List<Trip> tripsList;
    ListView tripRequestListView;
    DatabaseReference tripDbRef, tripAcceptedDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_trip_request);
        Intent intent = getIntent();
        String driverName = intent.getStringExtra("driverName");

        tripRequestListView = findViewById(R.id.tripRequestList);
        tripsList = new ArrayList<>();

        tripDbRef = FirebaseDatabase.getInstance().getReference("Trips-pending");
        tripAcceptedDbRef = FirebaseDatabase.getInstance().getReference("Trips");
        tripDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripsList.clear();

                for (DataSnapshot tripDatasnap : dataSnapshot.getChildren()){
                    Trip trips = tripDatasnap.getValue(Trip.class);
                    if(Objects.equals(trips.driver, driverName) && Objects.equals(trips.status, "Pending")) {
                        tripsList.add(trips);
                    }
                }
                RequestAdapter adapter = new RequestAdapter(GetTripRequestActivity.this,tripsList);
                tripRequestListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tripRequestListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Trip trips = tripsList.get(position);
                showUpdateDialog(trips.getId(), trips.getUserEmail(), trips.getDestination(), trips.getDistance(), trips.getVehicle(), trips.getPrice(), trips.getDriver());

                return false;
            }
        });
    }

    private void showUpdateDialog(final String id, String userEmail, String destination, String distance, String vehicle, String price, String driver){

        final AlertDialog.Builder mDialog = new AlertDialog.Builder(GetTripRequestActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_dialog, null);

        mDialog.setView(mDialogView);

        Button btnUpdate = mDialogView.findViewById(R.id.btnAccept);
        Button btnCancel = mDialogView.findViewById(R.id.btnCancel);
        mDialog.setTitle("Trip Request");
        final AlertDialog alertDialog = mDialog.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we will update data in database

                String newStatus = "Received";

                updateData(id, userEmail, destination, distance, vehicle, price, driver, newStatus);

                Toast.makeText(GetTripRequestActivity.this, "Request Accept!", Toast.LENGTH_SHORT).show();

                deleteRecord(id);
                alertDialog.dismiss();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void updateData(String id, String userEmail, String destination, String distance, String vehicle, String price, String driver, String status){

        //creating database reference
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Trips");
        Trip trip = new Trip(id, userEmail, destination, distance, vehicle, price, driver, status);
        DbRef.setValue(trip);
    }

    private void deleteRecord(String id){
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Trips-pending").child(id);
        DbRef.removeValue();
    }
}