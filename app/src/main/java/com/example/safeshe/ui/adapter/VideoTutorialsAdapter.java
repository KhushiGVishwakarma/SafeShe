package com.example.safeshe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;

import java.util.List;

public class VideoTutorialsAdapter extends RecyclerView.Adapter<VideoTutorialsAdapter.VideoViewHolder> {

    private final List<String[]> videoTutorials;
    private final Context context;

    public VideoTutorialsAdapter(Context context, List<String[]> videoTutorials) {
        this.context = context;
        this.videoTutorials = videoTutorials;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_video_tutorial_list_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String[] videoData = videoTutorials.get(position);
        holder.videoTitle.setText(videoData[0]);
        holder.videoDesc.setText(videoData[1]);
        holder.videoLinks.setText(videoData[2]);

        holder.videoLinks.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoData[2]));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoTutorials.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle, videoDesc, videoLinks;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoDesc = itemView.findViewById(R.id.videoDesc);
            videoLinks = itemView.findViewById(R.id.videoLinks);
        }
    }
}
