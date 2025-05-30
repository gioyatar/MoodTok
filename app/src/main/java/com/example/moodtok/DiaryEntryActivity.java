package com.example.moodtok;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryEntryActivity extends AppCompatActivity {

    private EditText activitiesInput, diaryEntryInput;
    private Spinner moodSpinner;
    private ImageView moodImage;
    private Button saveButton;
    private DiaryDbHelper dbHelper;  // Declare dbHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaryentry);

        // Initialize views
        activitiesInput = findViewById(R.id.listed_activities);
        diaryEntryInput = findViewById(R.id.diary_description);
        moodSpinner = findViewById(R.id.mood_spinner);
        moodImage = findViewById(R.id.diary_mood_img);
        saveButton = findViewById(R.id.btnSaveEntry);

        // Set up spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mood_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);

        // Initialize dbHelper
        dbHelper = new DiaryDbHelper(this);

        // Change the image when a mood is selected
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0: // Good
                        moodImage.setImageResource(R.drawable.mood1);
                        break;
                    case 1: // Great
                        moodImage.setImageResource(R.drawable.mood2);
                        break;
                    case 2: // Neutral
                        moodImage.setImageResource(R.drawable.mood3);
                        break;
                    case 3: // Awful
                        moodImage.setImageResource(R.drawable.mood4);
                        break;
                    case 4: // Sad
                        moodImage.setImageResource(R.drawable.mood5);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when no item is selected
            }
        });

        // Save entry
        saveButton.setOnClickListener(v -> saveEntry());
    }

    private void saveEntry() {
        String activities = activitiesInput.getText().toString();
        String diaryEntry = diaryEntryInput.getText().toString();
        String mood = moodSpinner.getSelectedItem().toString();

        if (activities.isEmpty() || diaryEntry.isEmpty()) {
            Toast.makeText(DiaryEntryActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = dbHelper.insertData(activities, diaryEntry, mood);
            if (isInserted) {
                Toast.makeText(DiaryEntryActivity.this, "Entry Saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DiaryEntryActivity.this, "Error Saving Entry!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}