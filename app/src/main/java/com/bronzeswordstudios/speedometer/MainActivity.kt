package com.bronzeswordstudios.speedometer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowMetricsCalculator
import com.bronzeswordstudios.speedometer.mainBackground.DynamicBackground
import com.bronzeswordstudios.speedometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dynamicBackground: DynamicBackground

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val wheelAnimation: AnimationDrawable


        // note defaultDisplay is deprecated as of API 30. Will modify in future
        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val currentBounds = windowMetrics.bounds
        val displayPoint = Point()
        displayPoint.x = currentBounds.width()
        displayPoint.y = currentBounds.height()

        // getSize is also deprecated. Will modify in the future

        // set custom background
        dynamicBackground = DynamicBackground(this, displayPoint.x)
        binding.dynamicBackground.addView(dynamicBackground)

        // start wheel animation
        binding.dynamicWheel.apply {
            setBackgroundResource(R.drawable.wheel_animation)
            wheelAnimation = background as AnimationDrawable
        }
        wheelAnimation.start()

        // set on click listeners here
        binding.startButton.setOnClickListener {
            val intent = Intent(this, Speedometer::class.java)
            startActivity(intent)
        }

        binding.quitButton.setOnClickListener {
            this.finish()
        }
    }

    // thread control here
    override fun onResume() {
        dynamicBackground.resume()
        super.onResume()
    }

    override fun onPause() {
        dynamicBackground.pause()
        super.onPause()
    }

}

