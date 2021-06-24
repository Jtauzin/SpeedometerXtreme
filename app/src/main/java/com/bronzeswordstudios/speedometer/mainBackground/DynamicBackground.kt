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
        while (running) {
            draw()
            update()
            control()
        }
    }

    private fun draw(){
        val surfaceHolder : SurfaceHolder = holder
        if (surfaceHolder.surface.isValid) {
            val canvas: Canvas = surfaceHolder.lockCanvas()
            val paint = Paint()
            canvas.drawARGB(255, 135, 206, 235)
            for (cloud in clouds){
                canvas.drawBitmap(cloud.getBitmap(), cloud.getX().toFloat(), cloud.getY().toFloat(), paint)
            }
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun update(){
        for (cloud in clouds){
            cloud.update()
        }
    }

    private fun control(){
        try {
            Thread.sleep(13)
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        if (clouds.size < 10){
            clouds.add(
                Cloud(this.context, screenX
            )
            )
        }
    }

    fun resume(){
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
        running = false
        try {
            thread.join()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}