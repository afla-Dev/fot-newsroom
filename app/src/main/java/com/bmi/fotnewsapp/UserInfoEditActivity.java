package com.bmi.fotnewsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoEditActivity extends AppCompatActivity {
    private EditText etUsername, etEmail;
    private TextView tvUsername, tvEmail;
    private Button btnOk, btnCancel, btnEditInfo, btnSignOut;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        btnEditInfo = findViewById(R.id.btnEditInfo);
        btnSignOut = findViewById(R.id.btnSignOut);

        // Firebase setup
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // Load current data
        loadUserInfo();

        // Button click listeners
        btnOk.setOnClickListener(v -> saveUserInfo());
        btnCancel.setOnClickListener(v -> toggleEditMode(false));
        btnEditInfo.setOnClickListener(v -> toggleEditMode(true));
        btnSignOut.setOnClickListener(v -> showSignOutConfirmation());

        // Default mode: view only
        toggleEditMode(false);
    }

    private void loadUserInfo() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    etUsername.setText(username);
                    etEmail.setText(email);
                    tvUsername.setText(username);
                    tvEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserInfoEditActivity.this, "Failed to load user info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleEditMode(boolean editMode) {
        etUsername.setVisibility(editMode ? View.VISIBLE : View.GONE);
        etEmail.setVisibility(editMode ? View.VISIBLE : View.GONE);
        btnOk.setVisibility(editMode ? View.VISIBLE : View.GONE);
        btnCancel.setVisibility(editMode ? View.VISIBLE : View.GONE);

        tvUsername.setVisibility(editMode ? View.GONE : View.VISIBLE);
        tvEmail.setVisibility(editMode ? View.GONE : View.VISIBLE);
        btnEditInfo.setVisibility(editMode ? View.GONE : View.VISIBLE);
    }

    private void saveUserInfo() {
        String newUsername = etUsername.getText().toString().trim();
        String newEmail = etEmail.getText().toString().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.child("username").setValue(newUsername);
        userRef.child("email").setValue(newEmail).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tvUsername.setText(newUsername);
                tvEmail.setText(newEmail);
                toggleEditMode(false);
                Toast.makeText(this, "User info updated ✅", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSignOutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Really want to sign out?")
                .setPositiveButton("OK", (dialog, which) -> signOut())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
    }
}
