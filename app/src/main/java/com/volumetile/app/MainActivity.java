package com.volumetile.app;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

/**
 * Transparent, no-UI Activity.
 * When the user taps the launcher icon, this immediately fires
 * a volume-key event to surface the system volume panel, then finishes itself.
 *
 * On Android 9+ we use AudioManager.adjustVolume with FLAG_SHOW_UI
 * which pops the full volume panel (all streams) without changing any level.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openVolumePanel();

        // Close this transparent activity immediately so it doesn't appear in recents
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 300);
    }

    static void openVolumePanel(android.content.Context context) {
        AudioManager audio = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        if (audio != null) {
            // FLAG_SHOW_UI shows the panel; ADJUST_SAME doesn't change the volume
            audio.adjustVolume(AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
        }
    }

    private void openVolumePanel() {
        openVolumePanel(this);
    }
}
