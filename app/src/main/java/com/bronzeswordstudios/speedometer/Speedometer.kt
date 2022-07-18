package com.bronzeswordstudios.speedometer

import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bronzeswordstudios.speedometer.databinding.ActivitySpeedometerBinding

const val LOCATION_CODE = 1

class Speedometer : AppCompatActivity() {
    // TODO: 6/26/2021 88 MPH achievement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpeedometerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                /* Here we implement location manager and location listener to check for a change
                 in location. If a change is detected, pull the detected speed from the new location
                  and display to the user*/
                val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                val locationListener = LocationListener { p0 ->
                    val speed = convertToMPH(p0.speed)
                    // update the progress bar and text view
                    binding.speedBar.progress = defineProgress(speed)
                    binding.speedNumber.text = speed.toInt().toString()
                }
                // request out updates
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    500,
                    1f,
                    locationListener
                )
            }

            else -> {
                // request permissions if the user has not confirmed them
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_CODE
                )
            }
        }


    }

    private fun convertToMPH(speed: Float): Float {
        return speed * 2.237f
    }

    private fun defineProgress(speed: Float): Int {
        var progressInt = ((speed / 120) * 100) * 0.75f
        if (progressInt > 75) {
            progressInt = 75f
        }
        return progressInt.toInt()
    }
}