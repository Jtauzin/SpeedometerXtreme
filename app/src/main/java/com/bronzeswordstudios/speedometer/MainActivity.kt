package com.bronzeswordstudios.speedometer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowMetricsCalculator
import com.bronzeswordstudios.speedometer.mainBackground.DynamicBackground

class MainActivity : AppCompatActivity() {

    private lateinit var dynamicBackground: DynamicBackground

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up views and buttons for work
        val dynBackgroundView: FrameLayout = findViewById(R.id.dynamicBackground)
        val startButton: TextView = findViewById(R.id.startButton)
        val quitButton: TextView = findViewById(R.id.quitButton)
        val randomFacts: TextView = findViewById(R.id.randomFacts)
        val factView: LinearLayout = findViewById(R.id.factView)
        val closeText: Button = findViewById(R.id.factCloseButton)
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
        dynBackgroundView.addView(dynamicBackground)

        // start wheel animation
        val wheelImageView: ImageView = findViewById<ImageView>(R.id.dynamicWheel).apply {
            setBackgroundResource(R.drawable.wheel_animation)
            wheelAnimation = background as AnimationDrawable
        }
        wheelAnimation.start()

        // set on click listeners here
        startButton.setOnClickListener {
            val intent = Intent(this, Speedometer::class.java)
            startActivity(intent)
        }

        closeText.setOnClickListener {
            factView.visibility = View.INVISIBLE
        }

        randomFacts.setOnClickListener {
            factView.visibility = View.VISIBLE
        }

        quitButton.setOnClickListener {
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

