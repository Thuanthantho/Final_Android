package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

        tripRequestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip trips = tripsList.get(position);
                showUpdateDialog(trips.getId(), trips.getUserEmail(), trips.getDestination(), trips.getDistance(), trips.getVehicle(), trips.getPrice(), trips.getDriver());
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

                startActivity(new Intent(GetTripRequestActivity.this, OnTheWayActivity.class));
                finish();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord(id);
                alertDialog.dismiss();
                Toast.makeText(GetTripRequestActivity.this, "Cancel trip successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData(String id, String userEmail, String destination, String distance, String vehicle, String price, String driver, String status){

        //creating database reference

        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Trips");
        String newId = DbRef.push().getKey();
        Trip trip = new Trip(newId, userEmail, destination, distance, vehicle, price, driver, status);
        assert newId != null;
        DbRef.child(newId).setValue(trip);
    }

    private void deleteRecord(String id){
        DatabaseReference pendingDbRef = FirebaseDatabase.getInstance().getReference("Trips-pending").child(id);
        pendingDbRef.removeValue();
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