package com.imquarantined.util.helper

import android.app.Activity
import android.content.Context
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.imquarantined.util.helper.Toaster.showToast
import timber.log.Timber

/* Created by ashiq.buet16 **/

object SensorUtil {
    fun setSensor(TYPE: Int, sensorEventListener: SensorEventListener?, activity: Activity): Boolean {
        val sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(TYPE)
        if (sensor == null) {
            Timber.i("Device doesn't support barometer. Using Standard Pressure")
            return false
        }
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        return true
    }
    fun getSealevelPressure(alt: Float, p: Float): Float {
        return (p / Math.pow(1 - (alt / 44330.0f).toDouble(), 5.255)).toFloat()
    }
}