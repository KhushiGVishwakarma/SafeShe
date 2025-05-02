package com.example.safeshe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.safeshe.R;

import java.util.List;

public class SafetyGadgetsAdapter extends RecyclerView.Adapter<SafetyGadgetsAdapter.GadgetViewHolder> {

    private final List<String[]> safetyGadgets;
    private final Context context;

    public SafetyGadgetsAdapter(Context context, List<String[]> safetyGadgets) {
        this.context = context;
        this.safetyGadgets = safetyGadgets;
    }

    @NonNull
    @Override
    public GadgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_safety_gadgets_list_item, parent, false);
        return new GadgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GadgetViewHolder holder, int position) {
        String[] gadgetData = safetyGadgets.get(position);
        holder.gadgetTitle.setText(gadgetData[0]);
        holder.gadgetDesc.setText(gadgetData[1]);

        int imageResId = context.getResources().getIdentifier(gadgetData[2], "drawable", context.getPackageName());
        Glide.with(context)
                .load(imageResId)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.gadgetImage);
    }

    @Override
    public int getItemCount() {
        return safetyGadgets.size();
    }

    public static class GadgetViewHolder extends RecyclerView.ViewHolder {
        TextView gadgetTitle, gadgetDesc;
        ImageView gadgetImage;

        public GadgetViewHolder(@NonNull View itemView) {
            super(itemView);
            gadgetTitle = itemView.findViewById(R.id.gadgetTitle);
            gadgetDesc = itemView.findViewById(R.id.gadgetDesc);
            gadgetImage = itemView.findViewById(R.id.gadgetImage);
        }
    }
}
