package com.example.safeshe.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;

import java.util.List;

public class SafetyTipsAdapter extends RecyclerView.Adapter<SafetyTipsAdapter.SafetyTipViewHolder> {

    private final List<String> safetyTips;
    private final Context context;

    public SafetyTipsAdapter(Context context, List<String> safetyTips) {
        this.context = context;
        this.safetyTips = safetyTips;
    }

    @NonNull
    @Override
    public SafetyTipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_safety_tips_list_item, parent, false);
        return new SafetyTipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyTipViewHolder holder, int position) {
        holder.bind(safetyTips.get(position));
    }

    @Override
    public int getItemCount() {
        return safetyTips.size();
    }

    public class SafetyTipViewHolder extends RecyclerView.ViewHolder {
        TextView tipText;

        public SafetyTipViewHolder(@NonNull View itemView) {
            super(itemView);
            tipText = itemView.findViewById(R.id.tips);

            itemView.setOnLongClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Safety Tip", tipText.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        public void bind(String tip) {
            tipText.setText(tip);
        }
    }
}
