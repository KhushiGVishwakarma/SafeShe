package com.example.safeshe.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.safeshe.R;
import com.example.safeshe.common.Constants;
import com.example.safeshe.config.Prefs;
import com.example.safeshe.databinding.FragmentSettingsBinding;
import com.example.safeshe.service.SosService;
import com.example.safeshe.ui.activity.MainActivity;
import com.example.safeshe.util.SosUtil;
import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private MaterialSwitch switchTheme;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "theme_pref";
    private static final String THEME_KEY = "is_dark";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.activity_settings_title));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.activity_settings_desc));
        }

        binding.switchShakeDetection.setChecked(Prefs.getBoolean(Constants.SETTINGS_SHAKE_DETECTION, false));
        binding.switchShakeDetection.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Prefs.putBoolean(Constants.SETTINGS_SHAKE_DETECTION, isChecked);
            MainActivity.shakeDetection.setValue(isChecked);
        });
        binding.shakeDetectionContainer.setOnClickListener(v -> binding.switchShakeDetection.toggle());

        binding.switchSendSms.setChecked(Prefs.getBoolean(Constants.SETTINGS_SEND_SMS, true));
        binding.switchSendSms.setOnCheckedChangeListener((buttonView, isChecked) -> Prefs.putBoolean(Constants.SETTINGS_SEND_SMS, isChecked));
        binding.sendSmsContainer.setOnClickListener(v -> binding.switchSendSms.toggle());

        binding.switchSendNotification.setChecked(Prefs.getBoolean(Constants.SETTINGS_SEND_NOTIFICATION, true));
        binding.switchSendNotification.setOnCheckedChangeListener((buttonView, isChecked) -> Prefs.putBoolean(Constants.SETTINGS_SEND_NOTIFICATION, isChecked));
        binding.sendNotificationContainer.setOnClickListener(v -> binding.switchSendNotification.toggle());

        binding.switchPlaySiren.setChecked(Prefs.getBoolean(Constants.SETTINGS_PLAY_SIREN, false));
        binding.switchPlaySiren.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Prefs.putBoolean(Constants.SETTINGS_PLAY_SIREN, isChecked);
            if (!isChecked) {
                SosService.stopSiren();
                SosUtil.stopSiren();
            }
        });
        binding.playSirenContainer.setOnClickListener(v -> binding.switchPlaySiren.toggle());

        binding.switchCallEmergencyService.setChecked(Prefs.getBoolean(Constants.SETTINGS_CALL_EMERGENCY_SERVICE, false));
        binding.switchCallEmergencyService.setOnCheckedChangeListener((buttonView, isChecked) -> Prefs.putBoolean(Constants.SETTINGS_CALL_EMERGENCY_SERVICE, isChecked));
        binding.callEmergencyServiceContainer.setOnClickListener(v -> binding.switchCallEmergencyService.toggle());

        switchTheme = view.findViewById(R.id.switch_theme);
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, getContext().MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(THEME_KEY, false);

        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        switchTheme.setChecked(isDarkMode);

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(THEME_KEY, isChecked);
            editor.apply();
        });

        binding.switchSendNearbyAlerts.setChecked(Prefs.getBoolean(Constants.SETTINGS_SEND_NEARBY_ALERTS, false));

        binding.switchSendNearbyAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Prefs.putBoolean(Constants.SETTINGS_SEND_NEARBY_ALERTS, isChecked);
        });

        binding.sendNearbyAlertsContainer.setOnClickListener(v -> binding.switchSendNearbyAlerts.toggle());


        return view;
    }
}