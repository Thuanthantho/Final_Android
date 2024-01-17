package com.example.bikerer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileActivity extends AppCompatActivity {
    String[] item = {"Male", "Female", "Prefer not to say"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    EditText etName;
    EditText etAge;
    DatabaseReference profileDbRef;
    Button btnInsertData;
    String genderSelected;
    private FirebaseAuth mAuth;
    String curentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        curentEmail = currentUser.getEmail();
        etName = findViewById(R.id.etProfileName);
        etAge  = findViewById(R.id.etAge);
        btnInsertData = findViewById(R.id.insertBtn);
        profileDbRef = FirebaseDatabase.getInstance().getReference("Profiles");
        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<String>(this, R.layout.select_item, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                genderSelected = adapterView.getItemAtPosition(position).toString();
            }
        });

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProfileData();
                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
            }
        });
    }

    private void insertProfileData(){

        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        String gender = genderSelected;
        String userEmail = curentEmail;
        String id = profileDbRef.push().getKey();

        Profile userProfile = new Profile(id,name,age,gender,userEmail);
        assert id != null;
        profileDbRef.child(id).setValue(userProfile);
        Toast.makeText(UpdateProfileActivity.this,"Update Profile successfully!",Toast.LENGTH_SHORT).show();
    }
}