package com.imquarantined.ui

import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imquarantined.R
import com.imquarantined.ui.base.BaseActivity
import com.imquarantined.util.helper.DialogUtil
import com.imquarantined.util.helper.FirebaseAuthUtil
import com.imquarantined.util.helper.NetworkUtil


class MainActivity : BaseActivity() {
    
    private lateinit var mNavController: NavController
    private var mNoNetworkDialog: AlertDialog? = null
    private val mMainViewModel: MainViewModel by viewModels()


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbarId(): Int {
        return NO_TOOLBAR
    }

    override fun onClick(v: View?) {
        super.onClick(v)
    }

    override fun afterOnCreate() {
        initBottomNavigation()
        observeData()
        FirebaseAuthUtil.init()
    }

    private fun initBottomNavigation() {
        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        mNavController = Navigation.findNavController(this, R.id.fragment_nav_host)
        NavigationUI.setupWithNavController(nav, mNavController)

    }

    private fun observeData() {

    }

    override fun onConnectivityChanged(connected: Boolean) {
        super.onConnectivityChanged(connected)

        mNoNetworkDialog?.dismiss()
        if (connected) return

        mNoNetworkDialog = DialogUtil.noNetworkDialog(this,
            callback = object : DialogUtil.NoNetworkDialogCallback {
                override fun onClickRefresh() {
                    NetworkUtil.refresh()
                }
            })
    }


}
