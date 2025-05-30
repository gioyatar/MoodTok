package com.example.moodtok;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CalendarComponent {

    private final TextView monthYearText;
    private final ImageButton prevMonthBtn, nextMonthBtn;
    private final GridLayout calendarGrid;
    private final Calendar currentCalendar;

    private int selectedMoodResId = -1;
    public void setSelectedMood(int resId) {
        selectedMoodResId = resId;
    }

    private final Map<String, Map<Integer, Integer>> moodsPerMonth = new HashMap<>();
    private final Map<Integer, Integer> moodForDay = new HashMap<>();
    private static final String PREFS_NAME = "MoodPrefs";
    private static final String MOODS_KEY = "moods_per_month_json";

    private final SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();
    public CalendarComponent(TextView monthYearText, ImageButton prevMonthBtn, ImageButton nextMonthBtn, GridLayout calendarGrid) {
        this.monthYearText = monthYearText;
        this.prevMonthBtn = prevMonthBtn;
        this.nextMonthBtn = nextMonthBtn;
        this.calendarGrid = calendarGrid;
        this.currentCalendar = Calendar.getInstance();
        this.currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // Initialize SharedPreferences using calendarGrid context
        sharedPreferences = calendarGrid.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadAllMoodsFromPrefs();   // Load from SharedPreferences on startup
        loadCurrentMonthMoods();
        setupButtons();
        updateCalendar();
    }

    private void setupButtons() {
        prevMonthBtn.setOnClickListener(v -> {
            saveCurrentMonthMoods();        // Save current month moods before switching
            currentCalendar.add(Calendar.MONTH, -1);
            loadCurrentMonthMoods();        // Load moods for new month
            updateCalendar();
        });

        nextMonthBtn.setOnClickListener(v -> {
            saveCurrentMonthMoods();
            currentCalendar.add(Calendar.MONTH, 1);
            loadCurrentMonthMoods();
            updateCalendar();
        });
    }

    public void updateCalendar() {
        loadCurrentMonthMoods();
        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

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

        for (int i = 0; i < totalCells; i++) {
            TextView dayCell = new TextView(calendarGrid.getContext());
            dayCell.setGravity(Gravity.CENTER);
            dayCell.setTextSize(16);
            dayCell.setTextColor(Color.BLACK);
            dayCell.setPadding(0, 32, 0, 32);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 7, 1f);
            params.rowSpec = GridLayout.spec(i / 7, 1f);
            params.width = 0;
            params.height = dpToPx(48);
            params.setMargins(2, 2, 2, 2);
            params.setGravity(Gravity.FILL);
            dayCell.setLayoutParams(params);

            if (i >= offset && day <= daysInMonth) {
                final int currentDay = day;  // Make effectively final for lambda
                dayCell.setText(String.valueOf(currentDay));
                dayCell.setTag(currentDay);

                // Set layered background and get highlight drawable
                dayCell.setBackgroundResource(R.drawable.day_cell_background);
                LayerDrawable bgDrawable = (LayerDrawable) dayCell.getBackground();
                Drawable highlight = bgDrawable.findDrawableByLayerId(R.id.highlight);
                if (highlight != null) {
                    highlight.setAlpha(0);
                }

                boolean isToday = (currentCalendar.get(Calendar.YEAR) == todayYear &&
                        currentCalendar.get(Calendar.MONTH) == todayMonth &&
                        currentDay == todayDay);

                if (isToday && highlight != null) {
                    highlight.setAlpha(255);
                }

                dayCell.setOnClickListener(v -> {
                    if (selectedMoodResId != -1) {
                        if (dayCell.getCompoundDrawables()[0] == null) {
                            Drawable resizedDrawable = resizeDrawable(selectedMoodResId, 16, 16);
                            dayCell.setCompoundDrawables(resizedDrawable, null, null, null);
                            dayCell.setCompoundDrawablePadding(dpToPx(4));

                            // Save mood for this day
                            moodForDay.put(currentDay, selectedMoodResId);
                        }
                    }
                });

                // Load mood icon if saved for this day
                if (moodForDay.containsKey(currentDay)) {
                    int moodRes = moodForDay.get(currentDay);
                    Drawable resizedDrawable = resizeDrawable(moodRes, 16, 16);
                    dayCell.setCompoundDrawables(resizedDrawable, null, null, null);
                    dayCell.setCompoundDrawablePadding(dpToPx(4));
                }

                day++;
            } else {
                dayCell.setText("");
                dayCell.setBackgroundResource(R.drawable.day_cell_background);
                LayerDrawable bgDrawable = (LayerDrawable) dayCell.getBackground();
                Drawable highlight = bgDrawable.findDrawableByLayerId(R.id.highlight);
                if (highlight != null) {
                    highlight.setAlpha(0);
                }
            }

            calendarGrid.addView(dayCell);
        }
    }

    private int dpToPx(int dp) {
        float density = calendarGrid.getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private Drawable resizeDrawable(int resId, int widthDp, int heightDp) {
        Drawable drawable = calendarGrid.getContext().getResources().getDrawable(resId, null);
        int widthPx = dpToPx(widthDp);
        int heightPx = dpToPx(heightDp);
        drawable.setBounds(0, 0, widthPx, heightPx);
        return drawable;
    }

    private String getMonthKey() {
        return currentCalendar.get(Calendar.YEAR) + "-" + (currentCalendar.get(Calendar.MONTH) + 1);
    }

    private void saveCurrentMonthMoods() {
        moodsPerMonth.put(getMonthKey(), new HashMap<>(moodForDay));
        saveAllMoodsToPrefs();
    }

    private void saveAllMoodsToPrefs() {
        String json = gson.toJson(moodsPerMonth);
        sharedPreferences.edit().putString(MOODS_KEY, json).apply();
    }

    private void loadCurrentMonthMoods() {
        moodForDay.clear();
        Map<Integer, Integer> saved = moodsPerMonth.get(getMonthKey());
        if (saved != null) {
            moodForDay.putAll(saved);
        }
    }

    private void loadAllMoodsFromPrefs() {
        String json = sharedPreferences.getString(MOODS_KEY, null);
        if (json != null) {
            Type type = new TypeToken<HashMap<String, HashMap<Integer, Integer>>>(){}.getType();
            Map<String, Map<Integer, Integer>> savedMap = gson.fromJson(json, type);
            if (savedMap != null) {
                moodsPerMonth.clear();
                moodsPerMonth.putAll(savedMap);
            }
        }
    }


}
