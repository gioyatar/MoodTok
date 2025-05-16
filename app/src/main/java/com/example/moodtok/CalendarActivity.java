package com.example.moodtok;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private TextView monthYearText;
    private ImageButton prevMonthBtn, nextMonthBtn;
    private GridLayout calendarGrid;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_format);

        monthYearText = findViewById(R.id.monthYearText);
        prevMonthBtn = findViewById(R.id.prevMonth);
        nextMonthBtn = findViewById(R.id.nextMonth);
        calendarGrid = findViewById(R.id.calendarGrid);

        currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        prevMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.removeAllViews(); // Clear previous views
        calendarGrid.setColumnCount(7);
        calendarGrid.setRowCount(6);

        // Set month and year at top
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearText.setText(sdf.format(currentCalendar.getTime()));

        // Clone calendar
        Calendar tempCal = (Calendar) currentCalendar.clone();
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // What day does the month start on? (Sunday = 1)
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK); // 1–7

        int offset = firstDayOfWeek - 1; // Sunday = 0, Monday = 1...

        int totalCells = 42; // 6 weeks * 7 days
        int day = 1;

        for (int i = 0; i < totalCells; i++) {
            TextView dayCell = new TextView(this);
            dayCell.setGravity(Gravity.CENTER);
            dayCell.setTextSize(16);
            dayCell.setTextColor(Color.BLACK);
            dayCell.setPadding(0, 32, 0, 32);

            if (i >= offset && day <= daysInMonth) {
                dayCell.setText(String.valueOf(day));
                day++;
            } else {
                dayCell.setText(""); // empty cell
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 7, 1f);
            params.rowSpec = GridLayout.spec(i / 7, 1f);
            params.width = 0; // so weight works
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            dayCell.setLayoutParams(params);

            calendarGrid.addView(dayCell);
        }
    }
}
