package com.example.safeshe.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;
import com.example.safeshe.model.Incident;

import java.util.List;
import java.util.Locale;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder> {

    private List<Incident> incidentList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Incident incident);
    }

    public IncidentAdapter(List<Incident> incidentList, OnItemClickListener listener) {
        this.incidentList = incidentList;
        this.listener = listener;
    }

    public static class IncidentViewHolder extends RecyclerView.ViewHolder {
        TextView incidentType, tagView, location, description, timestamp;

        public IncidentViewHolder(View itemView) {
            super(itemView);
            incidentType = itemView.findViewById(R.id.incident_type);
            tagView = itemView.findViewById(R.id.tag_type);
            location = itemView.findViewById(R.id.incident_location);
            description = itemView.findViewById(R.id.incident_description);
            timestamp = itemView.findViewById(R.id.incident_timestamp);
        }

        public void bind(Incident incident, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(incident));
        }
    }

    @NonNull
    @Override
    public IncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incident, parent, false);
        return new IncidentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentViewHolder holder, int position) {
        Incident incident = incidentList.get(position);
        holder.incidentType.setText("Incident Type: " + incident.getIncidentType());
        holder.location.setText("Location: " + incident.getLocation());
        holder.description.setText("Description: " + incident.getDescription());

        // Format timestamp
        String formattedTime = "Time: Unknown";
        try {
            if (incident.getTimestamp() != null) {
                java.util.Date date = incident.getTimestamp().toDate();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM dd, yyyy 'at' h:mm a", java.util.Locale.getDefault());
                formattedTime = "Time: " + sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error if any
        }

        holder.timestamp.setText(formattedTime);

        holder.bind(incident, listener);

        String type = incident.getIncidentType();
        holder.tagView.setText(type.toUpperCase(Locale.ROOT));


        // Pick a color based on the incident type
        int color;
        switch (type.toLowerCase()) {
            case "harassment":
                color = android.graphics.Color.parseColor("#D32F2F"); // red
                break;
            case "cyberbullying":
                color = android.graphics.Color.parseColor("#1976D2"); // blue
                break;
            case "domestic violence":
                color = android.graphics.Color.parseColor("#7B1FA2"); // purple
                break;
            case "theft":
                color = android.graphics.Color.parseColor("#F57C00"); // orange
                break;
            default:
                color = android.graphics.Color.parseColor("#607D8B"); // grey
                break;
        }

        // Apply the tint to the background
        android.graphics.drawable.Drawable bg = androidx.core.content.ContextCompat.getDrawable(holder.tagView.getContext(), R.drawable.bg_incident_tag).mutate();
        bg.setColorFilter(new android.graphics.PorterDuffColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN));
        holder.tagView.setBackground(bg);

    }


    @Override
    public int getItemCount() {
        return incidentList.size();
    }
}
