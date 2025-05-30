package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CalendarComponent calendarComponent;
    private ImageView todoImageView;
    private ImageView diaryImageView;;
    private ImageView aboutImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView monthYearText = findViewById(R.id.monthYearText);
        ImageButton prevMonthBtn = findViewById(R.id.prevMonth);
        ImageButton nextMonthBtn = findViewById(R.id.nextMonth);
        GridLayout calendarGrid = findViewById(R.id.calendarGrid);

        todoImageView = findViewById(R.id.todo);
        diaryImageView = findViewById(R.id.diary);
        aboutImageView = findViewById(R.id.about);

        calendarComponent = new CalendarComponent(monthYearText, prevMonthBtn, nextMonthBtn, calendarGrid);
        findViewById(R.id.mood1).setOnClickListener(v ->
                calendarComponent.setSelectedMood(R.drawable.better));

        findViewById(R.id.mood2).setOnClickListener(v ->
                calendarComponent.setSelectedMood(R.drawable.happy));

        findViewById(R.id.mood3).setOnClickListener(v ->
                calendarComponent.setSelectedMood(R.drawable.neutral));

        findViewById(R.id.mood4).setOnClickListener(v ->
                calendarComponent.setSelectedMood(R.drawable.awful));

        findViewById(R.id.mood5).setOnClickListener(v ->
                calendarComponent.setSelectedMood(R.drawable.sad));

        if (todoImageView != null) {
            todoImageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                startActivity(intent);
            });
        }
        if (diaryImageView != null) {
            diaryImageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent);
            });
        }
        if (aboutImageView != null) {
            aboutImageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            });
        }
    }
}
