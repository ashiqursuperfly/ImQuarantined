package com.imquarantined.util.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat

import com.imquarantined.data.Const

/* Created by ashiq.buet16 **/

class GPSUtil  {

    fun unregisterLocationListener(context: Context, locationListener: LocationListener){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)

    }


    fun registerLocationListener(context: Context, locationListener: LocationListener) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.powerRequirement = Criteria.POWER_HIGH
            val locationProvider = locationManager.getBestProvider(criteria, true)

            locationManager.requestLocationUpdates(locationProvider!!, Const.Misc.locationRequestPeriodMillis, 0.0f, locationListener)

        }

    }

    fun isGpsEnabled(context: Context) : Boolean{
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }



}
/*val fused = LocationServices.getFusedLocationProviderClient(activity)
val locationRequest = LocationRequest()
locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
locationRequest.interval = Const.Misc.LocationRequestPeriodMillis
locationRequest.fastestInterval = Const.Misc.LocationRequestPeriodMillis/2
fused.requestLocationUpdates()*/
