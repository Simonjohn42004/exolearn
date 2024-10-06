package com.example.exolearnapp.learningmodel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exolearnapp.R;
import com.example.exolearnapp.learningmodel.adapters.ModuleAdapter;
import com.example.exolearnapp.learningmodel.model.Module;
import com.example.exolearnapp.utils.FirebaseUtils;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RoadmapActivity extends AppCompatActivity {

    private static final String TAG = "RoadmapActivity";
    private List<Module> modules = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap);

        // Load modules from Firestore
        loadModulesFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.roadmap_recycler_view);
        ModuleAdapter moduleAdapter = new ModuleAdapter(modules, this);
        recyclerView.setAdapter(moduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Load all modules from Firestore and store them in the list.
     */
    private void loadModulesFromFirestore() {
        FirebaseUtils.getInstance().loadModules(new FirebaseUtils.ModuleCallback() {
            @Override
            public void onModulesLoaded(QuerySnapshot queryDocumentSnapshots) {
                modules.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Module module = document.toObject(Module.class);
                    modules.add(module);
                }

                // Log the modules loaded
                Log.d(TAG, "Modules loaded: " + modules.size());

                // Navigate to LessonActivity with the first module for testing purposes
                if (!modules.isEmpty()) {
                    Intent intent = new Intent(RoadmapActivity.this, LessonActivity.class);
                    intent.putExtra("module", modules.get(0));  // Passing the first module
                    startActivity(intent);
                }
            }
        });
    }
}
