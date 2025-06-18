package com.bmi.fotnewsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail;
    private Button btnEditInfo, btnSignOut;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize views
        ImageButton backButton = findViewById(R.id.backButton);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnEditInfo = findViewById(R.id.btnEditInfo);
        btnSignOut = findViewById(R.id.btnSignOut);

        // Load user data
        loadUserData();

        // Set up bottom navigation
        setupBottomNavigation();

        // Edit Info button click handler
        btnEditInfo.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserInfoEditActivity.class);
            startActivity(intent);
        });

        // Sign Out button click handler
        btnSignOut.setOnClickListener(v -> {
            signOutUser();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refreshing user data when returning from edit screen
        loadUserData();
    }

    private void loadUserData() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        // default value

    }

    private void signOutUser() {
        // Clear user session
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        prefs.edit().clear().apply();

        // Navigate to login screen and clear back stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, NewsCategoriesActivity.class));
                finish();
                return true;

            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_saved) {
                return true;
            } else if (id == R.id.nav_profile) {
                // Already on profile page
                return true;
            }
            return false;
        });
        // Highlight the profile icon as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
    }
}