package com.example.bikerer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    String recipientEmail;
    DatabaseReference chatDbRef;
    String messages;
    ArrayList<String> msgList = new ArrayList<>();
    ArrayAdapter<String> msgAdapter;
    ListView messageListView;
    FirebaseAuth mAuth;
    String currentEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentEmail = currentUser.getEmail();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            recipientEmail = bundle.getString("recipientEmail");
        }
        chatDbRef = FirebaseDatabase.getInstance().getReference("Chat");
        msgAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, msgList);

        messageListView = findViewById(R.id.messageAdapter);
        messageListView.setAdapter(msgAdapter);
        chatDbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msgList.clear();
                for (DataSnapshot chatDataSnapshot : snapshot.getChildren()) {
                    String recipientEmail1 = chatDataSnapshot.child("recipientEmail").getValue(String.class);
                    if (recipientEmail.equals(recipientEmail1)) {
                        DataSnapshot messagesSnapshot = chatDataSnapshot.child("messages");

                        for (DataSnapshot messageSnapshot : messagesSnapshot.getChildren()) {
                            String email = messageSnapshot.child("email").getValue(String.class);
                            String messageText = messageSnapshot.child("messageText").getValue(String.class);
                            msgList.add(messageText + "(" + email + ")");
                        }
                    }
                }
                msgAdapter.notifyDataSetChanged();
                ListView messageListView = findViewById(R.id.messageAdapter);
                msgAdapter = new ArrayAdapter<>(ChatActivity.this, android.R.layout.simple_list_item_1, msgList);
                messageListView.setAdapter(msgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Button sendBtn = findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(v -> {
            EditText textMessage = findViewById(R.id.message);
            messages = textMessage.getText().toString();
            insertChatMessage();
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

    private void insertChatMessage() {
        String email = recipientEmail;
        String messages1 = messages;

        String chatId = chatDbRef.push().getKey();


        chatDbRef.child(chatId).child("recipientEmail").setValue(email);

        String messageId = chatDbRef.child(chatId).child("messages").push().getKey();

        MessageDetails messageDetails = new MessageDetails(messageId, messages1, currentEmail);

        chatDbRef.child(chatId).child("messages").child(messageId).setValue(messageDetails);
    }


}
