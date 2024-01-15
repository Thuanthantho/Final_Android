package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetTripHistoryActivity extends AppCompatActivity {
    ListView tripListView;
    List<Trip> tripsList;

    DatabaseReference tripDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_trip_history);

        tripListView = findViewById(R.id.rvTrip);
        tripsList = new ArrayList<>();

        tripDbRef = FirebaseDatabase.getInstance().getReference("Trips");

        tripDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripsList.clear();

                for (DataSnapshot tripDatasnap : dataSnapshot.getChildren()){
                    Trip trips = tripDatasnap.getValue(Trip.class);
                    tripsList.add(trips);
                }

                ListAdapter adapter = new ListAdapter(GetTripHistoryActivity.this,tripsList);
                tripListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}