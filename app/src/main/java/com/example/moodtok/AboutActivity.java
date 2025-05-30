package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {
    private ImageView todoImageView;
    private ImageView diaryImageView;
    private ImageView dashboardImageView;
    private ImageView foxImageView;
    //private CalendarComponent calendarComponent; // Assuming CalendarComponent exists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        todoImageView = findViewById(R.id.todo);
        diaryImageView = findViewById(R.id.diary);
        dashboardImageView = findViewById(R.id.dashboard);

        // Initialize CalendarComponent with appropriate views if needed
        // calendarComponent = new CalendarComponent(monthYearText, prevMonthBtn, nextMonthBtn, calendarGrid);

        if (todoImageView != null) {
            todoImageView.setOnClickListener(v -> {
                Intent intent = new Intent(AboutActivity.this, ToDoActivity.class);
                startActivity(intent);
            });
        }
        if (foxImageView != null) {
            foxImageView.setOnClickListener(v -> {
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
        if (diaryImageView != null) {
            diaryImageView.setOnClickListener(v -> {
                Intent intent = new Intent(AboutActivity.this, DiaryActivity.class);
                startActivity(intent);
            });
        }
        if (dashboardImageView != null) {
            dashboardImageView.setOnClickListener(v -> {
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
    }
}