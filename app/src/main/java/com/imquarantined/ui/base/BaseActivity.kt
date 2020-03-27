package com.imquarantined.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import com.imquarantined.util.helper.NetworkUtil

/* Created by ashiq.buet16 **/

abstract class BaseActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var mMenu: Menu

    companion object {
        const val NO_TOOLBAR = -1
        const val NO_MENU = -1
        const val NO_MENU_ITEM = -1
    }

    abstract fun getLayoutId(): Int

    abstract fun getToolbarId(): Int

    open fun getMenuId(): Int {
        return NO_MENU
    }

    abstract fun afterOnCreate()

    open fun afterOnCreateOptionsMenu(menu: Menu?) {

    }

    open fun onConnectivityChanged(connected: Boolean) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        setToolbar()

        afterOnCreate()

        observeConnectivity()

    }

    override fun onResume() {
        super.onResume()

        /*
         * Sometimes if the app is in onPause() for long time
         * the network status returns wrong state.
         * So, onResume() just refresh the state
         */
        NetworkUtil.refresh()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    override fun onClick(v: View?) { }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if(getMenuId() == NO_MENU) return super.onCreateOptionsMenu(menu)

        menuInflater.inflate(getMenuId(), menu)

        if(menu != null) mMenu = menu

        afterOnCreateOptionsMenu(menu)

        return true
    }

    private fun setToolbar() {
        if(getToolbarId() == NO_TOOLBAR) return

        val toolbar = findViewById<Toolbar>(getToolbarId())
        if(toolbar != null) setSupportActionBar(toolbar)
    }

    fun setHomeEnabled() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showMenu(show: Boolean) {
        if(!::mMenu.isInitialized) return

        mMenu.forEach { item -> item.isVisible = show }
    }

    fun setClickListener(vararg views: View) {
        views.forEach { view -> view.setOnClickListener(this) }
    }

    private fun handleIntent(intent: Intent?) {
        /*
            if (intent?.action == Intent.ACTION_SEARCH) {
            }
        */
    }

    private fun observeConnectivity() {
        NetworkUtil.from(this).observe(this, Observer { connected ->
            onConnectivityChanged(connected)
        })
    }

    override fun onDestroy() {
        NetworkUtil.unregister(this)
        super.onDestroy()
    }

}