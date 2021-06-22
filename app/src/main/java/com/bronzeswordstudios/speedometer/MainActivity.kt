package com.bronzeswordstudios.speedometer

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton: TextView = findViewById(R.id.startButton)
        val quitButton: TextView = findViewById(R.id.quitButton)

        startButton.setOnClickListener {
            val intent = Intent(this, Speedometer::class.java)
            startActivity(intent)
            this.finish()
        }

        quitButton.setOnClickListener {
            this.finish()
        }
    }
}