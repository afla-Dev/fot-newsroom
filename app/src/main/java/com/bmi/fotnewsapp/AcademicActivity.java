package com.bmi.fotnewsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AcademicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.academic);

        // Register button click handler
        findViewById(R.id.registerButton).setOnClickListener(v -> {
            try {
                Uri uri = Uri.parse("https://your-university.edu/register");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            } catch (Exception e) {

            }
        });
        Button academicButton = findViewById(R.id.eventsButton);
        academicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcademicActivity.this, EventsActivity.class));
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
        academicButton = findViewById(R.id.sportsButton);
        academicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcademicActivity.this, NewsCategoriesActivity.class));
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}