package com.bronzeswordstudios.speedometer

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

const val LOCATION_CODE = 1

class Speedometer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speedometer)

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                /* Here we implement location manager and location listener to check for a change
                 in location. If a change is detected, pull the detected speed from the new location
                  and display to the user*/
                val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                val locationListener: LocationListener = object : LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        val speed: Float = p0.speed
                        // test code to show if speed is actually updating
                        Toast.makeText(this@Speedometer, "" + speed, Toast.LENGTH_SHORT).show()
                    }
                }
                // request out updates
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    1f,
                    locationListener
                )
            }
            //TODO: Update UI to display reasoning for permission request

            else -> {

                // request permissions if the user has not confirmed them
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_CODE
                )
            }
        }


    }
}