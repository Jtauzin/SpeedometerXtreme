package com.bronzeswordstudios.speedometer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class LocationService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}