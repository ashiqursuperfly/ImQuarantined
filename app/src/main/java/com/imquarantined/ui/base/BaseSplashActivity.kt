package com.imquarantined.ui.base

import android.os.Handler
import com.imquarantined.ui.base.BaseActivity


/* Created by ashiq.buet16 **/

abstract class BaseSplashActivity: BaseActivity() {

    private var mSplashTimeOut = false

    private val mHandler = Handler()
    private var mRunnable = Runnable {
        mSplashTimeOut = true
        afterSplash()
    }

    override fun getToolbarId(): Int {
        return NO_TOOLBAR
    }

    override fun getMenuId(): Int {
        return NO_MENU
    }

    abstract fun getSplashTimeMillis(): Long

    abstract fun afterSplash()

    override fun onResume() {
        super.onResume()

        mHandler.postDelayed(mRunnable, getSplashTimeMillis())
    }

    override fun onPause() {
        super.onPause()

        mHandler.removeCallbacks(mRunnable)
    }

    fun isSplashTimeout(): Boolean {
        return  mSplashTimeOut
    }
}