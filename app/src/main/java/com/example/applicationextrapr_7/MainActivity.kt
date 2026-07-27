package com.example.applicationextrapr_7

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        requestNotificationPermission()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPlayPause = findViewById<ImageButton>(R.id.btnPlayPause)
        val btnStop = findViewById<ImageButton>(R.id.btnStop)
        val btnShuffle = findViewById<ImageButton>(R.id.btnShuffle)
        val btnPrevious = findViewById<ImageButton>(R.id.btnPrevious)
        val btnNext = findViewById<ImageButton>(R.id.btnNext)

        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                sendCommandToService(MusicService.ACTION_PAUSE)
                btnPlayPause.setImageResource(R.drawable.ic_play)
                Toast.makeText(this, "Music Paused", Toast.LENGTH_SHORT).show()
                isPlaying = false
            } else {
                sendCommandToService(MusicService.ACTION_PLAY)
                btnPlayPause.setImageResource(R.drawable.ic_pause)
                Toast.makeText(this, "Music Playing", Toast.LENGTH_SHORT).show()
                isPlaying = true
            }
        }

        btnStop.setOnClickListener {
            sendCommandToService(MusicService.ACTION_STOP)
            btnPlayPause.setImageResource(R.drawable.ic_play)
            Toast.makeText(this, "Music Stopped", Toast.LENGTH_SHORT).show()
            isPlaying = false
        }

        btnShuffle.setOnClickListener {
            Toast.makeText(this, "Shuffle Clicked", Toast.LENGTH_SHORT).show()
        }

        btnPrevious.setOnClickListener {
            Toast.makeText(this, "Previous Clicked", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            Toast.makeText(this, "Next Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }

    private fun sendCommandToService(action: String) {
        val intent = Intent(this, MusicService::class.java).apply {
            putExtra(MusicService.EXTRA_ACTION, action)
        }
        startService(intent)
    }
}