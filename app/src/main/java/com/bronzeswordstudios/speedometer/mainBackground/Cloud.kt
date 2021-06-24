package com.bronzeswordstudios.speedometer.mainBackground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bronzeswordstudios.speedometer.R
import kotlin.random.Random

class Cloud(context: Context, private val screenX: Int) {
    private val bitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cloud)
    private val frameWidth = 237
    private val frameHeight = 87
    private val scaledBitmap : Bitmap = Bitmap.createScaledBitmap(bitmap, frameWidth, frameHeight, false)
    private val velocity = Random.nextInt(10) + 1
    private var x = Random.nextInt(screenX)
    private var y = Random.nextInt(100) + frameHeight

    fun update() {
        x -= velocity
        if(x < -frameWidth){
            x = screenX + frameWidth
        }
    }


    fun getBitmap() : Bitmap{
        return scaledBitmap
    }

    fun getX() : Int{
        return x
    }

    fun getY() : Int{
        return y
    }
}