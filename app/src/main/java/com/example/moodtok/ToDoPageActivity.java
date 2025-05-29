package com.example.moodtok;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ToDoPageActivity extends AppCompatActivity {

    EditText editTextTaskTitle, editTextTaskDescription, editTextDeadline;
    Button btnSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todopage); // Make sure the XML file is named correctly

        // Initialize views
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // Date picker for deadline
        editTextDeadline.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ToDoPageActivity.this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        editTextDeadline.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Save button logic
        btnSaveTask.setOnClickListener(view -> {
            String title = editTextTaskTitle.getText().toString().trim();
            String description = editTextTaskDescription.getText().toString().trim();
            String deadline = editTextDeadline.getText().toString().trim();

            if (title.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(ToDoPageActivity.this, "Please enter title and deadline", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(0, title, description, deadline);
            TaskDatabaseHelper db = new TaskDatabaseHelper(ToDoPageActivity.this);
            db.insertTask(task);

            Toast.makeText(ToDoPageActivity.this, "Task saved!", Toast.LENGTH_SHORT).show();
            finish(); // go back to MainActivity
        });
    }
}
