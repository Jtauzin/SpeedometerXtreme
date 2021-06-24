package com.bronzeswordstudios.speedometer

import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowMetrics
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bronzeswordstudios.speedometer.mainBackground.DynamicBackground

class MainActivity : AppCompatActivity() {

    private lateinit var dynamicBackground : DynamicBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dynBackgroundView : FrameLayout = findViewById(R.id.dynamicBackground)
        val startButton: TextView = findViewById(R.id.startButton)
        val quitButton: TextView = findViewById(R.id.quitButton)
        val wheelAnimation : AnimationDrawable
        val display = windowManager.defaultDisplay
        val displayPoint : Point = Point()
        display.getSize(displayPoint)
        dynamicBackground = DynamicBackground(this, displayPoint.x)

        dynBackgroundView.addView(dynamicBackground)

        val wheelImageView : ImageView = findViewById<ImageView>(R.id.dynamicWheel).apply{
            setBackgroundResource(R.drawable.wheel_animation)
            wheelAnimation = background as AnimationDrawable
        }
        wheelAnimation.start()

        startButton.setOnClickListener {
            val intent = Intent(this, Speedometer::class.java)
            startActivity(intent)
            this.finish()
        }

        quitButton.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        dynamicBackground.resume()
        super.onResume()
    }

    override fun onPause() {
        dynamicBackground.pause()
        super.onPause()
    }
}