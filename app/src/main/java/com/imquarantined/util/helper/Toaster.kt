package com.imquarantined.util.helper

import android.content.Context
import android.widget.Toast
import com.imquarantined.ImQuarantinedApp

/* Created by ashiq.buet16 **/

object Toaster {

    fun showToast(message: String, context: Context = ImQuarantinedApp.getApplicationContext()) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun showToast(message: Int, context: Context = ImQuarantinedApp.getApplicationContext()) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun showLongToast(message: String, context: Context = ImQuarantinedApp.getApplicationContext()) {
        show(context, message, Toast.LENGTH_LONG)
    }

    fun showLongToast(message: Int, context: Context = ImQuarantinedApp.getApplicationContext()) {
        show(context, message, Toast.LENGTH_LONG)
    }

    private fun show(context: Context, message: String, length: Int) {
        Toast.makeText(context, message, length).show()
    }

    private fun show(context: Context, message: Int, length: Int) {
        Toast.makeText(context, message, length).show()
    }
}