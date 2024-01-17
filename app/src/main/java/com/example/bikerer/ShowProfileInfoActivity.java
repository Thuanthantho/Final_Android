package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ShowProfileInfoActivity extends AppCompatActivity {
    Button updateBtn;
    TextView email, name, age, gender, tvName;
    DatabaseReference profileDbRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_info);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateBtn = findViewById(R.id.updateProfileBtn);
        email = findViewById(R.id.showEmail);
        email.setText(currentUser.getEmail());

        tvName = findViewById(R.id.tvName);
        name = findViewById(R.id.nameTv);
        age = findViewById(R.id.ageTv);
        gender = findViewById(R.id.genderTv);

        profileDbRef = FirebaseDatabase.getInstance().getReference("Profiles");

        profileDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot profileDatasnap : dataSnapshot.getChildren()){
                    Profile userProfile = profileDatasnap.getValue(Profile.class);
                    if(Objects.equals(userProfile.userEmail, currentUser.getEmail())){
                        tvName.setText(userProfile.getName());
                        name.setText(userProfile.getName());
                        age.setText(userProfile.getAge());
                        gender.setText(userProfile.getGender());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowProfileInfoActivity.this, UpdateProfileActivity.class));
            }
        });

    }
}