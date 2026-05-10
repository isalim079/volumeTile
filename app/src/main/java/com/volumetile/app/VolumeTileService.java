package com.volumetile.app;

import android.media.AudioManager;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

/**
 * Quick Settings Tile (appears in the notification shade swipe-down panel).
 *
 * Installation:
 *   1. Swipe down twice to expand the full QS panel
 *   2. Tap the pencil/edit icon at the bottom
 *   3. Find "Volume Panel" and drag it into your active tiles
 *
 * Tapping the tile opens the system volume panel without changing any volume level.
 */
public class VolumeTileService extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();
        // Keep the tile always active (not a toggle)
        Tile tile = getQsTile();
        if (tile != null) {
            tile.setState(Tile.STATE_ACTIVE);
            tile.updateTile();
        }
    }

    @Override
    public void onClick() {
        super.onClick();

        // Collapse the QS panel first, then show the volume panel
        // This gives a clean UX: panel closes, volume UI appears on top
        collapseStatusBar();

        // Small delay to let the panel collapse animation finish
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audio != null) {
                audio.adjustVolume(AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
            }
        }, 200);
    }

    private void collapseStatusBar() {
        // Use the standard tile dismiss approach
        // On API 33+ use the proper method; below we rely on the UX flow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // The system automatically collapses after onClick on API 33+
        } else {
            try {
                // Reflection approach for older APIs
                Object service = getSystemService("statusbar");
                if (service != null) {
                    Class<?> cls = service.getClass();
                    cls.getMethod("collapsePanels").invoke(service);
                }
            } catch (Exception ignored) {
                // Not critical; volume panel will still open
            }
        }
    }
}
