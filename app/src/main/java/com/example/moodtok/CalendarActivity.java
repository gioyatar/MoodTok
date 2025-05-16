package com.example.moodtok;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity {

    private final TextView monthYearText;
    private final ImageButton prevMonthBtn, nextMonthBtn;
    private final GridLayout calendarGrid;
    private final Calendar currentCalendar;

    public CalendarActivity(TextView monthYearText, ImageButton prevMonthBtn, ImageButton nextMonthBtn, GridLayout calendarGrid) {
        this.monthYearText = monthYearText;
        this.prevMonthBtn = prevMonthBtn;
        this.nextMonthBtn = nextMonthBtn;
        this.calendarGrid = calendarGrid;
        this.currentCalendar = Calendar.getInstance();
        this.currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        setupButtons();
        updateCalendar();
    }

    private void setupButtons() {
        prevMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });
    }

    public void updateCalendar() {
        calendarGrid.removeAllViews();
        calendarGrid.setColumnCount(7);
        calendarGrid.setRowCount(6);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearText.setText(sdf.format(currentCalendar.getTime()));

        Calendar tempCal = (Calendar) currentCalendar.clone();
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);

        int offset = firstDayOfWeek - 1;

        int totalCells = 42;
        int day = 1;

        // **Start here: Resolve the fontFamily attribute once**
        TypedValue typedValue = new TypedValue();
        boolean found = calendarGrid.getContext().getTheme().resolveAttribute(android.R.attr.fontFamily, typedValue, true);

        Typeface customFont = null;
        if (found) {
            int fontResId = typedValue.resourceId;
            if (fontResId != 0) {
                customFont = ResourcesCompat.getFont(calendarGrid.getContext(), fontResId);
            }
        }
        // **End font resolving**

        // Then the loop creating the day cells
        for (int i = 0; i < totalCells; i++) {
            TextView dayCell = new TextView(calendarGrid.getContext());
            dayCell.setGravity(Gravity.CENTER);
            dayCell.setTextSize(16);
            dayCell.setTextColor(Color.BLACK);
            dayCell.setPadding(0, 32, 0, 32);

            if (customFont != null) {
                dayCell.setTypeface(customFont);
            }

            if (i >= offset && day <= daysInMonth) {
                dayCell.setText(String.valueOf(day));
                day++;
            } else {
                dayCell.setText("");
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 7, 1f);
            params.rowSpec = GridLayout.spec(i / 7, 1f);
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            dayCell.setLayoutParams(params);

            calendarGrid.addView(dayCell);
        }
    }
}
