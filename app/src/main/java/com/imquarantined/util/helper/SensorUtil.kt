package com.imquarantined.util.helper

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.imquarantined.util.helper.Toaster.showToast


/* Created by ashiq.buet16 **/

object SensorUtil {
    fun setSensor(TYPE: Int, sensorEventListener: SensorEventListener?, activity: Activity): Boolean {
        var sensor: Sensor? = null
        val sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(TYPE)
        if (sensor == null) {
            val errorMessage = "Device Doesn't Support This Sensor"
            showToast(errorMessage)
            return false
        }
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI)
        return true
    }
}