package com.example.applicationextrapr_7

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MusicService : Service() {
    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_STOP = "action_stop"
        const val EXTRA_ACTION = "extra_action"
        const val CHANNEL_ID = "MusicServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MusicService", "Service Created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra(EXTRA_ACTION)
        Log.d("MusicService", "OnStartCommand with action: $action")

        when (action) {
            ACTION_PLAY -> {
                playMusic()
            }
            ACTION_PAUSE -> {
                pauseMusic()
            }
            ACTION_STOP -> {
                stopMusic()
                stopSelf()
            }
            else -> {
                toggleMusic()
            }
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun getNotification(content: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MP3 Player")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_play) // Use existing icon
            .setOngoing(true)
            .build()
    }

    private fun initMediaPlayer() {
        if (mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.song)
                if (mediaPlayer == null) {
                    Log.e("MusicService", "Failed to create MediaPlayer - check if song.mp3 is valid")
                    return
                }
                mediaPlayer?.isLooping = true
                Log.d("MusicService", "MediaPlayer initialized")
            } catch (e: Exception) {
                Log.e("MusicService", "Error creating MediaPlayer", e)
            }
        }
    }

    private fun playMusic() {
        initMediaPlayer()
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                Log.d("MusicService", "Playback started")
                startForeground(NOTIFICATION_ID, getNotification("Music is playing"))
            }
        }
    }

    private fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                Log.d("MusicService", "Playback paused")
                startForeground(NOTIFICATION_ID, getNotification("Music is paused"))
            }
        }
    }

    private fun toggleMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                pauseMusic()
            } else {
                playMusic()
            }
        } ?: playMusic()
    }

    private fun stopMusic() {
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
                Log.d("MusicService", "MediaPlayer stopped and released")
            } catch (e: Exception) {
                Log.e("MusicService", "Error stopping MediaPlayer", e)
            }
        }
        mediaPlayer = null
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onDestroy() {
        Log.d("MusicService", "Service Destroyed")
        stopMusic()
        super.onDestroy()
    }
}