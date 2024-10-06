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
import com.example.exolearnapp.learningmodel.activities.LessonActivity;
import com.example.exolearnapp.learningmodel.model.Module;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private List<Module> moduleList;
    private Context context;

    public ModuleAdapter(List<Module> moduleList, Context context) {
        this.moduleList = moduleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.moduleTitle.setText(module.getModuleTitle());

        // Set click listener for the card view
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LessonActivity.class);
            intent.putExtra("module", module);  // Passing the selected module
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleTitle;
        CardView cardView;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.module_title);
            cardView = itemView.findViewById(R.id.module_card);
        }
    }
}

