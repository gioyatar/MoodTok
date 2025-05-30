package com.example.moodtok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryActivity extends AppCompatActivity {

    ImageView addIcon;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
