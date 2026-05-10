package com.volumetile.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;

public class MainActivity extends Activity {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_ASKED_BATTERY = "asked_battery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openVolumePanel();
        maybeAskBatteryOptimizationExemption();
        moveTaskToBack(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        openVolumePanel();
        moveTaskToBack(true);
    }

    private void openVolumePanel() {
        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audio != null) {
            audio.adjustVolume(AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
        }
    }

    private void maybeAskBatteryOptimizationExemption() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager == null) {
            return;
        }

        String packageName = getPackageName();
        boolean ignoring = powerManager.isIgnoringBatteryOptimizations(packageName);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean askedBefore = prefs.getBoolean(KEY_ASKED_BATTERY, false);

        if (!ignoring && !askedBefore) {
            new AlertDialog.Builder(this)
                    .setTitle("Keep Volume Panel instant")
                    .setMessage("Allow battery optimization exemption for instant response.")
                    .setCancelable(false)
                    .setPositiveButton("Allow", (dialog, which) -> {
                        prefs.edit().putBoolean(KEY_ASKED_BATTERY, true).apply();
                        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:" + packageName));
                        try {
                            startActivity(intent);
                        } catch (Exception ignored) {
                            // Ignore if device blocks this settings screen.
                        }
                    })
                    .setNegativeButton("Skip", (dialog, which) ->
                            prefs.edit().putBoolean(KEY_ASKED_BATTERY, true).apply())
                    .show();
        }
    }
}
