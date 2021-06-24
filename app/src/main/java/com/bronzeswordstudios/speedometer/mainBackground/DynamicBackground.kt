package com.bronzeswordstudios.speedometer.mainBackground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception
import kotlin.concurrent.thread

class DynamicBackground(context: Context, private val screenX : Int) : SurfaceView(context), Runnable {


    private lateinit var thread : Thread
    private var running = true
    private val clouds : ArrayList<Cloud> = ArrayList()

    override fun run() {
        // here is our main loop
        while (running) {
            draw()
            update()
            control()
        }
    }

    private fun draw(){
        /* lock the canvas and draw our sky background as well as clouds*/
        val surfaceHolder : SurfaceHolder = holder
        if (surfaceHolder.surface.isValid) {
            val canvas: Canvas = surfaceHolder.lockCanvas()
            val paint = Paint()
            // sky color courtesy of google
            canvas.drawARGB(255, 135, 206, 235)
            for (cloud in clouds){
                // cycle through each cloud object and draw it at it's new position
                canvas.drawBitmap(cloud.getBitmap(), cloud.getX().toFloat(), cloud.getY().toFloat(), paint)
            }
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun update(){
        for (cloud in clouds){
            // run the update method on each cloud
            cloud.update()
        }
    }

    private fun control(){
        // control FPS here
        try {
            Thread.sleep(13)
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        // add clouds to cloud array if they do not exist already
        if (clouds.size < 7){
            clouds.add(
                Cloud(this.context, screenX
            )
            )
        }
    }

    fun resume(){
        // control thread here for onResume behaviour
        running = true
        thread = Thread(this)
        try {
            thread.start()
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun pause(){
        // control thread here for onPause behaviour
        running = false
        try {
            thread.join()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}