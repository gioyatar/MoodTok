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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView monthYearText = findViewById(R.id.monthYearText);
        ImageButton prevMonthBtn = findViewById(R.id.prevMonth);
        ImageButton nextMonthBtn = findViewById(R.id.nextMonth);
        GridLayout calendarGrid = findViewById(R.id.calendarGrid);

        todoImageView = findViewById(R.id.todo);

        calendarComponent = new CalendarComponent(monthYearText, prevMonthBtn, nextMonthBtn, calendarGrid);
        if (todoImageView != null) {
            todoImageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                startActivity(intent);
            });
        }
    }
}
