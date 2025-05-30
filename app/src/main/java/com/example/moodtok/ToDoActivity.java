package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moodtok.R;
import com.example.moodtok.Task;
import com.example.moodtok.TaskAdapter;
import com.example.moodtok.TaskDatabaseHelper;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskDatabaseHelper dbHelper;
    private ArrayList<Task> taskList;
    private ImageView dashboardImageView;
    private ImageView diaryImageView;
    private ImageView aboutImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new TaskDatabaseHelper(this);
        taskList = dbHelper.getAllTasks();

        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

        dashboardImageView = findViewById(R.id.dashboard);
        diaryImageView = findViewById(R.id.diary);
        aboutImageView = findViewById(R.id.about);

        adapter.setOnItemClickListener(task -> {
            Intent intent = new Intent(ToDoActivity.this, ToDoPageEditActivity.class);
            intent.putExtra("id", task.getId());
            intent.putExtra("title", task.getTitle());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("deadline", task.getDeadline());
            startActivity(intent);
        });

        ImageView addIcon = findViewById(R.id.addIcon);
        addIcon.setOnClickListener(view -> {
            startActivity(new Intent(this, ToDoPageActivity.class));
        });

        if (dashboardImageView != null) {
            dashboardImageView.setOnClickListener(v -> {
                Intent intent = new Intent(ToDoActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
        if (diaryImageView != null) {
            diaryImageView.setOnClickListener(v -> {
                Intent intent = new Intent(ToDoActivity.this, DiaryActivity.class);
                startActivity(intent);
            });
        }
        if (aboutImageView != null) {
            aboutImageView.setOnClickListener(v -> {
                Intent intent = new Intent(ToDoActivity.this, AboutActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }


}
