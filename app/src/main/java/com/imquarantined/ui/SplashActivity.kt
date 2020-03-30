package com.imquarantined.ui

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.imquarantined.R
import com.imquarantined.ui.base.BaseSplashActivity
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.NetworkUtil


/* Created by ashiq.buet16 **/

class SplashActivity : BaseSplashActivity() {

    private var mNoNetworkDialog: AlertDialog? = null

    override fun getLayoutId(): Int { return R.layout.activity_splash }

    override fun getSplashTimeMillis(): Long {
        return 3500L
    }

    override fun afterOnCreate() {
//        AndroidUtil.logKeyHash()
    }

    override fun afterSplash() {

         if(NetworkUtil.hasNoNetwork()) {
            NetworkUtil.refresh()
            return
        }

        launchMainActivity()

        finish()
    }

    override fun onConnectivityChanged(connected: Boolean) {
        super.onConnectivityChanged(connected)

        mNoNetworkDialog?.dismiss()
        if(connected) {
            if(isSplashTimeout() && !isFinishing) afterSplash()
            return
        }

        if(isSplashTimeout()) {
            mNoNetworkDialog = DialogUtil.noNetworkDialog(this,
                callback = object: DialogUtil.NoNetworkDialogCallback {
                    override fun onClickRefresh() {
                        NetworkUtil.refresh()
                    }
                })
        }
    }


    private fun launchMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
