package com.imquarantined.util.helper

import java.util.*

/* Created by ashiq.buet16 **/
class CustomTimerTask: TimerTask() {
    private var mCallback: TimerCallback? = null

    fun setCallback(callback: TimerCallback) {
        mCallback = callback;
    }

    override fun run() {
        mCallback?.onTime()
    }

    interface TimerCallback {
        fun onTime()
    }
}
