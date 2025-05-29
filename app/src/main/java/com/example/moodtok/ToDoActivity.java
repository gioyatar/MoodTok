package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodtok.Task;
import com.example.moodtok.TaskAdapter;
import com.example.moodtok.TaskDatabaseHelper;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskDatabaseHelper dbHelper;
    private ArrayList<Task> taskList;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }
}
