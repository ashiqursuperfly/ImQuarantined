package com.imquarantined.util.helper

import android.content.Context
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import timber.log.Timber

/* Created by ashiq.buet16 **/

object SensorUtil {
    fun setSensor(TYPE: Int, sensorEventListener: SensorEventListener, context: Context): Boolean {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(TYPE)
        if (sensor == null) {
            Timber.i("Device doesn't support barometer. Using Standard Pressure")
            return false
        }
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        return true
    }

    fun unregisterListener(context: Context, sensorEventListener: SensorEventListener){
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(sensorEventListener)
    }

    fun getSealevelPressure(alt: Float, p: Float): Float {
        return (p / Math.pow(1 - (alt / 44330.0f).toDouble(), 5.255)).toFloat()
    }
}