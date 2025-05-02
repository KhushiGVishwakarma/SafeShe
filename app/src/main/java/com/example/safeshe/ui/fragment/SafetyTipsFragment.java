package com.example.safeshe.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;
import com.example.safeshe.databinding.FragmentSafetyTipsBinding;
import com.example.safeshe.ui.adapter.SafetyGadgetsAdapter;
import com.example.safeshe.ui.adapter.SafetyTipsAdapter;
import com.example.safeshe.ui.adapter.VideoTutorialsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SafetyTipsFragment extends Fragment {

    private FragmentSafetyTipsBinding binding;
    private final List<String> safetyTipsList = new ArrayList<>();
    private final List<String[]> videoTutorialsList = new ArrayList<>();
    private final List<String[]> safetyGadgetsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSafetyTipsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.app_name));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.app_moto));
        }

        loadSafetyTips();
        loadVideoTutorials();
        loadSafetyGadgets();

        setupRecyclerView(binding.recyclerViewSafetyTips, new SafetyTipsAdapter(requireContext(), safetyTipsList));
        setupRecyclerView(binding.recyclerViewVideoTutorials, new VideoTutorialsAdapter(requireContext(), videoTutorialsList));
        setupRecyclerView(binding.recyclerViewSafetyGadgets, new SafetyGadgetsAdapter(requireContext(), safetyGadgetsList));

        return view;
    }




    private void loadSafetyTips() {
        safetyTipsList.clear();
        for (int i = 1; i <= 21; i++) {
            int resId = getResources().getIdentifier("safety_tips_tip_" + i, "string", requireContext().getPackageName());

            if (resId != 0) {
                String tip = getString(resId);
                safetyTipsList.add(tip);
                Log.d("SafetyTipsFragment", "Loaded Safety Tip: " + tip);
            } else {
                Log.e("SafetyTipsFragment", "Missing string: safety_tips_tip_" + i);
            }
        }
    }


    private void loadVideoTutorials() {
        videoTutorialsList.clear();
        for (int i = 1; i <= 20; i++) {
            int titleResId = getResources().getIdentifier("video_tutorial_" + i + "_title", "string", requireContext().getPackageName());
            int descResId = getResources().getIdentifier("video_tutorial_" + i + "_desc", "string", requireContext().getPackageName());
            int linkResId = getResources().getIdentifier("video_tutorial_" + i + "_link", "string", requireContext().getPackageName());

            if (titleResId != 0 && descResId != 0 && linkResId != 0)
            {
                String title = getString(titleResId);
                String desc = getString(descResId);
                String link = getString(linkResId);
                videoTutorialsList.add(new String[]{title, desc, link});
                Log.d("SafetyTipsFragment", "Loaded Video Tutorial: " + title);
            } else {
                Log.e("SafetyTipsFragment", "Missing video tutorial: " + i);
            }
        }
    }


    private void loadSafetyGadgets() {
        safetyGadgetsList.clear();
        for (int i = 1; i <= 20; i++) {
            int titleResId = getResources().getIdentifier("gadget_" + i + "_title", "string", requireContext().getPackageName());
            int descResId = getResources().getIdentifier("gadget_" + i + "_desc", "string", requireContext().getPackageName());
            int imageResId = getResources().getIdentifier("gadget_" + i + "_image", "string", requireContext().getPackageName());

            if (titleResId != 0 && descResId != 0 && imageResId != 0) {
                String title = getString(titleResId);
                String desc = getString(descResId);
                String image = getString(imageResId);
                safetyGadgetsList.add(new String[]{title, desc, image});
                Log.d("SafetyTipsFragment", "Loaded Safety Gadget: " + title);
            } else {
                Log.e("SafetyTipsFragment", "Missing gadget: " + i);
            }
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }
}