package com.example.exolearnapp.learningmodel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exolearnapp.R;
import com.example.exolearnapp.learningmodel.activities.QuizActivity;
import com.example.exolearnapp.learningmodel.model.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;
    private Context context;

    public LessonAdapter(List<Lesson> lessonList, Context context) {
        this.lessonList = lessonList;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.lessonTitle.setText(lesson.getTitle());

        // Set click listener for the card view
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuizActivity.class);
            // Pass the current lesson to the quiz activity
            intent.putExtra("lesson", lesson);   // Pass the selected lesson
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle;
        CardView cardView;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            cardView = itemView.findViewById(R.id.lesson_card);
        }
    }
}

