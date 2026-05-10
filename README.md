# 🔊 Volume Panel — Quick Tile App
### For OnePlus Nord 5 (Android 14+)

Opens the **full system volume panel** (Media · Ring · Notification · Alarm) without pressing any hardware button.

---

## ✅ What This App Does

| Feature | Details |
|---|---|
| 🏠 Launcher icon tap | Opens volume panel instantly |
| ⬇️ Quick Settings tile | Tap the tile in your notification shade |
| 🔇 No permission needed | Works with standard `MODIFY_AUDIO_SETTINGS` |
| 🪟 Transparent activity | Zero visual flash — just the volume panel appears |

---

## 🛠 How to Build & Install

### Option A — Android Studio (Recommended)

1. Open **Android Studio** (Hedgehog 2023.1.1 or newer)
2. **File → Open** → select the `VolumeQuickTile` folder
3. Wait for Gradle sync to finish
4. Connect your OnePlus Nord 5 via USB (enable USB debugging in Developer Options)
5. Press ▶ **Run** (Shift+F10)

### Option B — Command Line

```bash
# Make sure ANDROID_HOME is set and you have SDK + build tools
cd VolumeQuickTile
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 Setting Up the Quick Settings Tile

After installing:

1. **Swipe down twice** from the top of your screen to expand the full QS panel
2. Tap the **✏️ pencil / Edit** icon at the bottom of the tile grid
3. Scroll down in the list of available tiles
4. Find **"Volume Panel"** (with the speaker icon)
5. **Long-press and drag** it into your active tiles area
6. Tap **Done**

Now tap the **Volume Panel** tile anytime to open the sound sliders! 🎉

---

## 🏠 Using the Launcher Icon

The app also installs a home screen icon labeled **"Volume Panel"**.  
Tap it → volume panel opens → transparent activity closes automatically.  
It will **not appear in your recent apps** list.

---

## 🔧 Technical Details

| Item | Value |
|---|---|
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |
| Language | Java |
| Volume method | `AudioManager.adjustVolume(ADJUST_SAME, FLAG_SHOW_UI)` |
| QS Tile API | `android.service.quicksettings.TileService` |

The `ADJUST_SAME` flag triggers the system UI without actually changing any volume level — it's a clean, read-only panel trigger.

---

## 🛡 Permissions

```xml
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```

This is a **normal permission** — no runtime prompt to the user required.

---

## ❓ Troubleshooting

**Tile not showing up?**  
→ Make sure the app is installed, then check Edit tiles again. Try rebooting once.

**Volume panel opens behind other apps?**  
→ This is normal on some launchers. The panel will still be fully functional.

**Build fails on Gradle sync?**  
→ Check your `local.properties` has `sdk.dir` pointing to your Android SDK path.

---

*Built for OnePlus Nord 5 · Android 14 · OxygenOS 15*
