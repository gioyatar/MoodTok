package com.example.moodtok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    private Spinner themeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load the previously saved theme before showing UI
        loadTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        themeSpinner = findViewById(R.id.themeSpinner);

        // Load current theme into spinner
        String currentTheme = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
                .getString("theme", "Light");
        String[] themes = getResources().getStringArray(R.array.themes_list);
        int position = Arrays.asList(themes).indexOf(currentTheme);
        if (position >= 0) {
            themeSpinner.setSelection(position);
        }

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (isFirst) {
                    isFirst = false; // Skip first trigger
                    return;
                }

                String selectedTheme = parent.getItemAtPosition(pos).toString();

                SharedPreferences.Editor editor = getSharedPreferences("ThemePrefs", MODE_PRIVATE).edit();
                editor.putString("theme", selectedTheme);
                editor.apply();

                recreate(); // Restart activity to apply new theme
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // This method loads the correct theme based on saved preference
    private void loadTheme() {
        String theme = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
                .getString("theme", "Devil Hunter");

        switch (theme) {
            case "Luna":
                setTheme(R.style.Theme_AppCompat_Luna);
                break;
            case "Dark Slayer":
                setTheme(R.style.Theme_AppCompat_DarkSlayer);
                break;
            case "Devil Hunter":
                setTheme(R.style.Theme_AppCompat_DevilHunter);
                break;
            default:
                setTheme(R.style.Theme_AppCompat_DevilHunter); // fallback
                break;
        }
    }

    // Called when the About CardView is clicked
    public void openDiaryLayout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}