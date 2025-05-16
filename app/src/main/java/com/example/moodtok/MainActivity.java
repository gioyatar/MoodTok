package com.example.moodtok;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CalendarActivity calendarActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView monthYearText = findViewById(R.id.monthYearText);
        ImageButton prevMonthBtn = findViewById(R.id.prevMonth);
        ImageButton nextMonthBtn = findViewById(R.id.nextMonth);
        GridLayout calendarGrid = findViewById(R.id.calendarGrid);

        calendarActivity = new CalendarActivity(monthYearText, prevMonthBtn, nextMonthBtn, calendarGrid);
    }
}
