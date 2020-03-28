package com.imquarantined.util.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.imquarantined.data.Const

/* Created by ashiq.buet16 **/

class GPSUtil  {

    fun findLocation(activity: Activity, locationListener: LocationListener) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.powerRequirement = Criteria.POWER_HIGH
            val locationProvider = locationManager.getBestProvider(criteria, true)

            locationManager.requestLocationUpdates(locationProvider!!, Const.Misc.LocationRequestPeriod, 0.0f, locationListener)

        }

    }

    fun isGpsEnabled(activity: Activity) : Boolean{
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }



}