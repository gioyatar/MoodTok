package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

public class DiaryActivity extends AppCompatActivity {

    ImageView addIcon;
    RecyclerView recyclerView;
    private ImageView todoImageView;
    private ImageView dashboardImageView;
    private ImageView aboutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        todoImageView = findViewById(R.id.todo);
        dashboardImageView = findViewById(R.id.dashboard);
        aboutImageView = findViewById(R.id.about);
        addIcon = findViewById(R.id.addIcon);
        recyclerView = findViewById(R.id.diaryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, DiaryEntryActivity.class);
                startActivity(intent);
            }
        });
        if (todoImageView != null) {
            todoImageView.setOnClickListener(v -> {
                Intent intent = new Intent(DiaryActivity.this, ToDoActivity.class);
                startActivity(intent);
            });
        }
        if (dashboardImageView != null) {
            dashboardImageView.setOnClickListener(v -> {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
        if (aboutImageView != null) {
            aboutImageView.setOnClickListener(v -> {
                Intent intent = new Intent(DiaryActivity.this, AboutActivity.class);
                startActivity(intent);
            });
        }
    }
}
