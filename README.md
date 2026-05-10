# VolumeQuickTile

VolumeQuickTile adds a Quick Settings tile and launcher icon that both open the system volume panel using:

`AudioManager.adjustVolume(AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)`

No persistent notification, no background service, and no wakelock are used.

## Requirements

- Package: `com.volumetile.app`
- Language: Java
- `minSdk 26`
- `targetSdk 34`

## How To Add The Quick Settings Tile

1. Swipe down twice to open full QS panel
2. Tap pencil/edit icon
3. Find "Volume" tile
4. Drag it to active tiles
5. Tap Done

Now the tile works exactly like Torch: tap = volume panel opens instantly.

## Behavior

- QS tile is always `STATE_ACTIVE`
- Tile tap collapses QS, waits 200ms, then opens volume panel
- Launcher icon opens the same volume panel
- Launcher activity is transparent and `singleTask`
- Activity uses `moveTaskToBack(true)` so warm relaunch is fast
- One-time battery optimization exemption prompt is shown on first launch if needed

## Permissions

```xml
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
```
