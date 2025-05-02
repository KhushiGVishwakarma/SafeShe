package com.example.safeshe.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.safeshe.R;
import com.example.safeshe.databinding.FragmentIncidentHistoryBinding;
import com.example.safeshe.model.Incident;
import com.example.safeshe.ui.adapter.IncidentAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IncidentHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private IncidentAdapter adapter;
    private List<Incident> incidentList;
    private FirebaseFirestore db;
    private String userId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyView;
    private Button retryButton;
    private FragmentIncidentHistoryBinding binding;

    public IncidentHistoryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIncidentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up toolbar with back button
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.activity_incident_history_title));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.activity_incident_history_desc));
        }

        recyclerView = view.findViewById(R.id.recyclerViewIncidents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        emptyView = view.findViewById(R.id.emptyView);
        retryButton = view.findViewById(R.id.retryButton);

        swipeRefreshLayout.setOnRefreshListener(this::loadIncidents);

        incidentList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        adapter = new IncidentAdapter(incidentList, incident -> {
            // 🔍 Show details on click
            showIncidentDetailsDialog(incident);
        });
        recyclerView.setAdapter(adapter);

        loadIncidents();
        enableSwipeToDelete();

        retryButton.setOnClickListener(v -> {
            retryButton.setVisibility(View.GONE);
            loadIncidents();
        });
        binding.retryButton.setOnClickListener(v -> loadIncidents());

        return view;
    }

    private void loadIncidents() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.emptyView.setVisibility(View.GONE);
        binding.recyclerViewIncidents.setVisibility(View.GONE);
        binding.retryButton.setVisibility(View.GONE);

        db.collection("IncidentReports")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    incidentList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        try {
                            Incident incident = doc.toObject(Incident.class);
                            if (incident != null) {
                                incidentList.add(incident);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error loading an incident", Toast.LENGTH_SHORT).show();
                        }
                    }

                    adapter.notifyDataSetChanged();

                    binding.progressBar.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                    binding.retryButton.setVisibility(View.GONE);

                    if (incidentList.isEmpty()) {
                        binding.emptyView.setVisibility(View.VISIBLE);
                        binding.recyclerViewIncidents.setVisibility(View.GONE);
                    } else {
                        binding.emptyView.setVisibility(View.GONE);
                        binding.recyclerViewIncidents.setAlpha(0f);
                        binding.recyclerViewIncidents.setVisibility(View.VISIBLE);
                        binding.recyclerViewIncidents.animate().alpha(1f).setDuration(500).start();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to load incidents: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                    binding.emptyView.setVisibility(View.VISIBLE);
                    binding.recyclerViewIncidents.setVisibility(View.GONE);
                    binding.retryButton.setVisibility(View.VISIBLE);
                });
    }

    private void showIncidentDetailsDialog(Incident incident) {
        String formattedTime = "Unknown time";
        if (incident.getTimestamp() != null) {
            long millis = incident.getTimestamp().toDate().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy 'at' h:mm a", Locale.getDefault());
            formattedTime = sdf.format(new Date(millis));
        }



        new AlertDialog.Builder(getContext())
                .setTitle("Incident Details")
                .setMessage("Type: " + incident.getIncidentType() + "\n"
                        + "Location: " + incident.getLocation() + "\n"
                        + "Description: " + incident.getDescription() + "\n"
                        + "Time: " + formattedTime)
                .setPositiveButton("OK", null)
                .show();
    }


    private void enableSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // Not needed
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Incident incidentToDelete = incidentList.get(position);

                incidentList.remove(position);
                adapter.notifyItemRemoved(position);

                Snackbar.make(binding.getRoot(), "Incident deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            incidentList.add(position, incidentToDelete);
                            adapter.notifyItemInserted(position);
                        })
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                    db.collection("IncidentReports")
                                            .whereEqualTo("userId", userId)
                                            .whereEqualTo("timestamp", incidentToDelete.getTimestamp())
                                            .get()
                                            .addOnSuccessListener(snapshot -> {
                                                for (DocumentSnapshot doc : snapshot) {
                                                    doc.getReference().delete();
                                                }
                                            });
                                }
                            }
                        })
                        .show();
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
}
