package com.example.bikerer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.Gravity;
import android.util.TypedValue;


public class SosActivity extends AppCompatActivity {

    private DatabaseReference sosDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sosDatabaseReference = FirebaseDatabase.getInstance().getReference("sos_requests");

        // Display SOS requests when the activity is created
        displayAllSOSRequests();
    }

    private void displayAllSOSRequests() {
        sosDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the previous SOS requests before displaying the updated ones
                clearSOSRequests();

                // Display the SOS requests from the database
                displaySOSRequests(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if any
            }
        });
    }

    private void clearSOSRequests() {
        LinearLayout sosLayout = findViewById(R.id.sosLayout);
        sosLayout.removeAllViews();
    }

    private void displaySOSRequests(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            SosRequest sosRequest = child.getValue(SosRequest.class);
            if (sosRequest != null) {
                displaySOSRequest(sosRequest);
            }
        }
    }

    private void displaySOSRequest(SosRequest sosRequest) {
        LinearLayout sosLayout = findViewById(R.id.sosLayout);

        // Create a TextView with the SOS request information
        TextView requestTextView = new TextView(this);
        requestTextView.setText(sosRequest.getUserEmail() + " needs help at " + sosRequest.getTimestamp());
        requestTextView.setBackgroundResource(R.drawable.rounded_rectangle);
        requestTextView.setGravity(Gravity.CENTER); // Center the text
        requestTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Increase text size

        // Set layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16); // Add margin between SOS requests
        requestTextView.setLayoutParams(layoutParams);

        // Add the TextView to the layout
        sosLayout.addView(requestTextView);
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
