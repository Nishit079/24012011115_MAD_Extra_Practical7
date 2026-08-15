# 🎵 MP3 Player – Android Foreground Service Application

> **MAD Extra Practical 7** — Demonstrating Android Foreground Service with MediaPlayer for background music playback.

---

## 📋 Student Information

| Field | Details |
|---|---|
| **Enrollment No.** | 24012011115 |
| **Name** | Nishit Patel |
| **Subject** | Mobile Application Development (MAD) |
| **Practical** | Extra Practical 7 |

---

## 📖 Objective

To develop an Android application that implements a **Foreground Service** to play MP3 audio in the background. The app demonstrates how Android Services can run independently of the Activity lifecycle, allowing music playback to continue even when the user navigates away from the app.

---

## 🚀 Features

- ▶️ **Play / Pause** — Start and pause audio playback with a single toggle button
- ⏹️ **Stop** — Completely stop playback and release media resources
- ⏮️ **Previous** — Navigate to the previous track *(placeholder for future enhancement)*
- ⏭️ **Next** — Navigate to the next track *(placeholder for future enhancement)*
- 🔀 **Shuffle** — Shuffle playback order *(placeholder for future enhancement)*
- 🔔 **Foreground Notification** — Persistent notification showing current playback status
- 🎨 **Dark Theme UI** — Modern dark-themed player interface with album art display
- 📱 **Edge-to-Edge Display** — Utilizes full screen with proper system bar inset handling

---

## 🛠️ Tech Stack

| Technology | Details |
|---|---|
| **Language** | Kotlin |
| **IDE** | Android Studio |
| **Min SDK** | API 24 (Android 7.0 Nougat) |
| **Target SDK** | API 36 |
| **Build System** | Gradle (Kotlin DSL) |
| **UI Framework** | XML Layouts with ConstraintLayout |
| **Architecture** | Activity–Service Communication via Intents |

---



## 🏗️ Architecture & Working

### Component Overview

```
┌──────────────────────────────────────┐
│           MainActivity               │
│  ┌──────────────────────────────┐    │
│  │   Play/Pause  Stop  Shuffle  │    │
│  │   Previous    Next           │    │
│  └──────────┬───────────────────┘    │
│             │ sendCommandToService() │
└─────────────┼────────────────────────┘
              │ Intent (ACTION_PLAY /
              │         ACTION_PAUSE /
              │         ACTION_STOP)
              ▼
┌──────────────────────────────────────┐
│          MusicService                │
│  (Foreground Service)                │
│  ┌──────────────────────────────┐    │
│  │  MediaPlayer (song.mp3)      │    │
│  │  Notification Channel        │    │
│  └──────────────────────────────┘    │
└──────────────────────────────────────┘
```

### How It Works

1. **User launches the app** → `MainActivity` is displayed with player controls.
2. **User taps Play** → `MainActivity` sends an `Intent` with `ACTION_PLAY` to `MusicService`.
3. **`MusicService` receives the intent** → Initializes `MediaPlayer` with `song.mp3` from raw resources, starts playback, and promotes itself to a **Foreground Service** with a persistent notification.
4. **User taps Pause** → `ACTION_PAUSE` is sent; `MediaPlayer.pause()` is called and the notification updates.
5. **User taps Stop** → `ACTION_STOP` is sent; `MediaPlayer` is stopped and released, the foreground notification is removed, and the service stops itself.
6. **Service destruction** → On `onDestroy()`, any active `MediaPlayer` is properly released to free system resources.

---

## 📑 Key Source Files

### `MainActivity.kt`

Handles the user interface and button click listeners. Communicates with the `MusicService` via explicit `Intents` using `startService()`. Also manages runtime permission requests for `POST_NOTIFICATIONS` on Android 13+ (API 33).

### `MusicService.kt`

A `Service` subclass that manages background audio playback using `MediaPlayer`. Key responsibilities:
- **Notification Channel creation** — Required for Android 8.0+ (API 26) to display foreground service notifications.
- **Foreground Service management** — Calls `startForeground()` to keep the service alive during playback.
- **MediaPlayer lifecycle** — Handles initialization, play, pause, stop, and release operations with proper error handling and logging.
- **`START_STICKY` return flag** — Ensures the service is restarted by the system if killed due to low memory.

### `activity_main.xml`

Dark-themed player layout using `ConstraintLayout` with:
- Title bar displaying "MP3 Player"
- Album art `ImageView` with 1:1 aspect ratio
- `CardView` control panel with rounded corners containing five `ImageButton` controls (Shuffle, Previous, Play/Pause, Next, Stop)

---

## 🔐 Permissions

The following permissions are declared in `AndroidManifest.xml`:

| Permission | Purpose |
|---|---|
| `FOREGROUND_SERVICE` | Required to run the service as a foreground service |
| `FOREGROUND_SERVICE_MEDIA_PLAYBACK` | Specifies the foreground service type for media playback (Android 14+) |
| `POST_NOTIFICATIONS` | Required for displaying notifications on Android 13+ (runtime permission) |

---

## ⚙️ Setup & Installation

### Prerequisites

- **Android Studio** Ladybug or later
- **JDK 11** or higher
- **Android SDK** with API 36 installed
- An Android device or emulator running **API 24+**

### Steps to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Nishit079/24012011115_MAD_Extra_Practical7.git
   ```

2. **Open in Android Studio:**
   - Launch Android Studio
   - Select `File → Open` and navigate to the cloned project directory

3. **Sync Gradle:**
   - Android Studio will automatically prompt to sync Gradle files
   - Click **Sync Now** if prompted

4. **Run the application:**
   - Connect a physical device via USB or start an Android emulator
   - Click the **Run ▶** button or press `Shift + F10`

5. **Grant notification permission** when prompted (on Android 13+)

---

## 📸 Application Screenshots

| Player UI | Notification |
|---|---|
| Dark-themed music player interface with album art and playback controls | Persistent foreground notification displaying playback status |

---

## 📚 Key Concepts Demonstrated

| Concept | Description |
|---|---|
| **Foreground Service** | A service that the user is actively aware of, shown via a notification, and is less likely to be killed by the system |
| **MediaPlayer API** | Android's built-in API for audio and video playback from raw resources |
| **Notification Channels** | Required on Android 8.0+ for categorizing and managing notification importance |
| **Runtime Permissions** | Requesting `POST_NOTIFICATIONS` permission at runtime for Android 13+ compliance |
| **Intent-based Communication** | Using explicit Intents with extras to send commands from Activity to Service |
| **Edge-to-Edge UI** | Modern full-screen display with proper WindowInsets handling |
| **Service Lifecycle** | Managing `onCreate()`, `onStartCommand()`, and `onDestroy()` for proper resource management |

---

## 📝 Dependencies

```kotlin
implementation(libs.androidx.activity.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.androidx.constraintlayout)
implementation(libs.androidx.core.ktx)
implementation(libs.material)
```

---

## 📄 License

This project is developed as part of the **Mobile Application Development (MAD)** curriculum for academic purposes.

---

<p align="center">
  <b>Developed by Nishit Patel — Enrollment No. 24012011115</b><br>
  <i>MAD Extra Practical 7 — Android Foreground Service with MediaPlayer</i>
</p>
