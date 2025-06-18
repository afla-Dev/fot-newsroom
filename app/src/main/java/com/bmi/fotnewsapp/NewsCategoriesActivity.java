package com.bmi.fotnewsapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsCategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_categories);

        // Bottom Navigation Setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // Already here
                    return true;
                } else if (id == R.id.nav_search) {
                    startActivity(new Intent(NewsCategoriesActivity.this, SettingsActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_saved) {
                    // Handle saved click
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(NewsCategoriesActivity.this, UserProfileActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Academic Button Click Listener
        Button academicButton = findViewById(R.id.academicButton);
        academicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsCategoriesActivity.this, AcademicActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // More Details Button Click Listener
        Button moreDetailsButton = findViewById(R.id.moreDetailsButton);
        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle more details click
            }
        });

        Button eventsButton = findViewById(R.id.eventsButton);
        eventsButton.setOnClickListener(v -> {
            startActivity(new Intent(NewsCategoriesActivity.this, EventsActivity.class));
        });
    }
}