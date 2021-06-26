package com.bronzeswordstudios.speedometer

import android.app.LoaderManager
import android.content.Intent
import android.content.Loader
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bronzeswordstudios.speedometer.mainBackground.DynamicBackground
import com.bronzeswordstudios.speedometer.query.QueryLoader

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {

    private lateinit var dynamicBackground: DynamicBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // set up views and buttons for work
        val dynBackgroundView: FrameLayout = findViewById(R.id.dynamicBackground)
        val startButton: TextView = findViewById(R.id.startButton)
        val quitButton: TextView = findViewById(R.id.quitButton)
        // TODO: 6/25/2021 fix wheel animation so it is on the ground
        val wheelAnimation: AnimationDrawable

        // note defaultDisplay is deprecated as of API 30. Will modify in future
        val display = windowManager.defaultDisplay
        val displayPoint = Point()

        // getSize is also deprecated. Will modify in the future
        display.getSize(displayPoint)

        // set custom background
        dynamicBackground = DynamicBackground(this, displayPoint.x)
        dynBackgroundView.addView(dynamicBackground)

        // start wheel animation
        val wheelImageView: ImageView = findViewById<ImageView>(R.id.dynamicWheel).apply {
            setBackgroundResource(R.drawable.wheel_animation)
            wheelAnimation = background as AnimationDrawable
        }
        wheelAnimation.start()
        // TODO: 6/25/2021 need to check for internet connection or service before calling
        loaderManager.initLoader(0, null, this)

        // set on click listeners here
        startButton.setOnClickListener {
            val intent = Intent(this, Speedometer::class.java)
            startActivity(intent)
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

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<String?> {
        return QueryLoader(this, "https://api.chucknorris.io/jokes/random")
    }

    override fun onLoadFinished(p0: Loader<String>?, p1: String?) {
        // TODO: 6/25/2021 need to move data to pop up on demand
        //Toast.makeText(this, p1, Toast.LENGTH_SHORT).show()
    }

    override fun onLoaderReset(p0: Loader<String>?) {
        loaderManager.destroyLoader(0)
    }


}

