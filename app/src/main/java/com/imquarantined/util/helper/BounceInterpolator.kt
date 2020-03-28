package com.imquarantined.util.helper

import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.pow


class BounceInterpolator(
    private var mAmplitude: Double = 1.0,
    private var mFrequency: Double = 10.0
): Interpolator {

    override fun getInterpolation(time: Float): Float {
        return (-1.0 * Math.E.pow(-time / mAmplitude) *
                cos(mFrequency * time) + 1).toFloat()
    }
}