package com.imquarantined.util.helper

import android.content.res.Resources

object DisplayMetricsUtil {

    enum class Density{
        LDPI,MDPI,HDPI,XHDPI,XXHDPI,XXXHDPI;

//        constructor(name: String, ordinal: Int, weight: Int) {
//            this.weight = weight
//        }
//
//        var weight:Int = 0
//
    }

    private val metrics = Resources.getSystem().displayMetrics

    fun getDensity(): Density {
        val density = metrics.density
        if (density >= 4.0) {
            return Density.XXXHDPI
        }
        if (density >= 3.0) {
            return Density.XXHDPI
        }
        if (density >= 2.0) {
            return Density.XHDPI
        }
        if (density >= 1.5) {
            return Density.HDPI
        }
        return if (density >= 1.0) {
            Density.MDPI
        } else Density.LDPI
    }

    fun getDeviceWidth(): Int {
        return metrics.widthPixels
    }
}