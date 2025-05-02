package com.example.safeshe.ui.fragment;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.safeshe.R;
import com.example.safeshe.databinding.FragmentReportIncidentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportIncidentFragment extends Fragment {

    private AutoCompleteTextView incidentTypeDropdown;
    private EditText incidentDescription;
    private EditText locationField;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FragmentReportIncidentBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private Button submitReportButton;

    public ReportIncidentFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReportIncidentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.app_name));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.app_moto));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        incidentTypeDropdown = view.findViewById(R.id.incident_type_dropdown);
        incidentDescription = view.findViewById(R.id.incident_description);
        locationField = view.findViewById(R.id.incident_location);
        submitReportButton = view.findViewById(R.id.submit_report_button);

        String[] incidentTypes = {"Harassment", "Stalking", "Physical Assault", "Cyberbullying", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, incidentTypes);
        incidentTypeDropdown.setAdapter(adapter);
        incidentTypeDropdown.setOnClickListener(v -> incidentTypeDropdown.showDropDown());

        getUserLocation();

        submitReportButton.setOnClickListener(v -> submitReport());
    }

    private void submitReport() {
        String incidentType = incidentTypeDropdown.getText().toString().trim();
        String description = incidentDescription.getText().toString().trim();
        String location = locationField.getText().toString().trim();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "User not authenticated.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        boolean hasError = false;
        if (incidentType.isEmpty()) {
            incidentTypeDropdown.setError("Required");
            hasError = true;
        }
        if (description.isEmpty()) {
            incidentDescription.setError("Required");
            hasError = true;
        }
        if (location.isEmpty()) {
            locationField.setError("Required");
            hasError = true;
        }
        if (hasError) return;

        Map<String, Object> incidentData = new HashMap<>();
        incidentData.put("userId", userId);
        incidentData.put("incidentType", incidentType);
        incidentData.put("description", description);
        incidentData.put("location", location);
        incidentData.put("timestamp", FieldValue.serverTimestamp());

        submitReportButton.setEnabled(false);

        db.collection("IncidentReports").add(incidentData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Incident reported successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                    getUserLocation();
                    submitReportButton.setEnabled(true);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to report incident.", Toast.LENGTH_SHORT).show();
                    submitReportButton.setEnabled(true);
                });
    }

    private void getUserLocation() {
        if (requireContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            try {
                                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    String locality = address.getLocality() != null ? address.getLocality() : "Unknown";
                                    String country = address.getCountryName() != null ? address.getCountryName() : "Unknown";
                                    locationField.setText(locality + ", " + country);
                                } else {
                                    locationField.setText("Unknown Location");
                                }
                            } catch (Exception e) {
                                locationField.setText("Unknown Location");
                            }
                        } else {
                            locationField.setText("Location not available");
                        }
                    });

        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserLocation();
        } else {
            Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        incidentTypeDropdown.setText("");
        incidentDescription.setText("");
        locationField.setText("");
    }
}
