package com.example.bikerer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView createnewAccount;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Use a custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the up button in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        System.out.println("LoginActivity onCreate called");  // Debugging statement

        createnewAccount = findViewById(R.id.createnewAccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Your Email Again");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Your Password is at least 6 letters");
        } else {
            progressDialog.setMessage("Please Wait");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Log.d("LoginActivity", "Authentication success");
                        gotoHomeActivity();
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("LoginActivity", "Authentication failed: " + task.getException().getMessage());
                        Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void gotoHomeActivity() {
        Log.d("LoginActivity", "gotoHomeActivity called");

        // Fetch user information
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Check the user's role based on email and password
            String userEmail = currentUser.getEmail();
            if (userEmail != null && userEmail.equals("admin@gmail.com")) {
                // User is an admin, check password
                String userPassword = inputPassword.getText().toString();
                if (userPassword.equals("123456")) {
                    // Password is correct, navigate to AdminActivity
                    Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(adminIntent);
                } else {
                    // Password is incorrect, show a message or handle it accordingly
                    Toast.makeText(LoginActivity.this, "Incorrect password for admin account", Toast.LENGTH_SHORT).show();
                }
            } else {
                // User is a regular user, navigate to HomeActivity
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                homeIntent.putExtra("userEmail", userEmail);
                startActivity(homeIntent);
            }
        } else {
            // Handle the case where the current user is null
            Toast.makeText(LoginActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }



    // Handle back button press in the action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

