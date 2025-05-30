package com.example.moodtok;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ToDoPageEditActivity extends AppCompatActivity {

    EditText editTextTaskTitle, editTextTaskDescription, editTextDeadline;
    Button btnSaveTask;
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todopageedit);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // Retrieve task data
        Intent intent = getIntent();
        taskId = intent.getIntExtra("id", -1);
        editTextTaskTitle.setText(intent.getStringExtra("title"));
        editTextTaskDescription.setText(intent.getStringExtra("description"));
        editTextDeadline.setText(intent.getStringExtra("deadline"));

        // Date picker
        editTextDeadline.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ToDoPageEditActivity.this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        editTextDeadline.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        btnSaveTask.setOnClickListener(view -> {
            String title = editTextTaskTitle.getText().toString().trim();
            String description = editTextTaskDescription.getText().toString().trim();
            String deadline = editTextDeadline.getText().toString().trim();

            if (title.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Please enter title and deadline", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(taskId, title, description, deadline);
            TaskDatabaseHelper db = new TaskDatabaseHelper(this);
            db.updateTask(task);

            Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
