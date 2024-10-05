package com.example.exolearnapp.searchscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exolearnapp.R;
import com.example.exolearnapp.modelclass.ExoPlanets;

import java.util.List;

public class ExoplanetAdapter extends RecyclerView.Adapter<ExoplanetAdapter.ViewHolder> {

    private final Context context;
    private List<ExoPlanets> exoplanetList;
    private OnItemClickListener listener;

    public ExoplanetAdapter(Context context, List<ExoPlanets> exoplanetList, OnItemClickListener listener) {
        this.context = context;
        this.exoplanetList = exoplanetList;
        this.listener = listener;
    }

    // Define interface for click listener
    public interface OnItemClickListener {
        void onItemClick(ExoPlanets exoplanet); // To be implemented in activity
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.planet_card_view, parent, false);
        return new ViewHolder(view);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExoPlanets exoplanet = exoplanetList.get(position);

        // Set KOI name
        holder.koiNameTextView.setText("KOI Name: " + exoplanet.getKoiName());

        // Set Kepler name or display "No Kepler name"
        String keplerName = exoplanet.getKeplerName();
        if (keplerName == null || keplerName.isEmpty()) {
            holder.keplerNameTextView.setText("No Kepler name");
        } else {
            holder.keplerNameTextView.setText("Kepler Name: " + keplerName);
        }

        // Set Disposition
        holder.dispositionTextView.setText("Planet disposition: " + exoplanet.getExoplanetArchiveDisposition());

        // Set Duration
        holder.durationTextView.setText("Duration: " + exoplanet.getTransitDuration() + " hrs");

        // Set Orbital Period
        holder.orbitalPeriodTextView.setText("Orbital Period: " + exoplanet.getOrbitalPeriod() + " days");

        // Set the click listener for each item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(exoplanet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exoplanetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView koiNameTextView, keplerNameTextView, dispositionTextView, durationTextView, orbitalPeriodTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            koiNameTextView = itemView.findViewById(R.id.koiNameTextView);
            keplerNameTextView = itemView.findViewById(R.id.keplerNameTextView);
            dispositionTextView = itemView.findViewById(R.id.dispositionTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            orbitalPeriodTextView = itemView.findViewById(R.id.orbitalPeriodTextView);
        }
    }
    public void updateList(List<ExoPlanets> filteredList){
        exoplanetList = filteredList;
    }
}

