package com.example.exolearnapp.learningmodel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exolearnapp.R;
import com.example.exolearnapp.learningmodel.adapters.LessonAdapter;
import com.example.exolearnapp.learningmodel.model.Lesson;
import com.example.exolearnapp.learningmodel.model.Module;

import java.util.List;

public class LessonActivity extends AppCompatActivity {

    private static final String TAG = "LessonActivity";
    private Module module;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Retrieve the module passed from RoadmapActivity
        if (getIntent().getSerializableExtra("module") != null) {
            module = (Module) getIntent().getSerializableExtra("module");

            RecyclerView recyclerView = findViewById(R.id.lesson_recycler_view);
            LessonAdapter lessonAdapter = new LessonAdapter(module.getLessons(), this);
            recyclerView.setAdapter(lessonAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Log the module details
            Log.d(TAG, "Loaded Module: " + module.getModuleTitle());

            // Display lessons in logs for now
            List<Lesson> lessons = module.getLessons();
            for (Lesson lesson : lessons) {
                Log.d(TAG, "Lesson: " + lesson.getTitle());
            }

            // For now, navigate to QuizActivity when lessons are displayed (test)
            Intent intent = new Intent(LessonActivity.this, QuizActivity.class);
            intent.putExtra("module", module);
            startActivity(intent);
        }
    }
}
