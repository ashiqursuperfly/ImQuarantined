package com.imquarantined.util.helper

import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.imquarantined.ImQuarantinedApp
import com.imquarantined.R

object SnackbarUtil {

    fun showLongSnackBar(
        view: View,
        message: String,
        colorRes: Int = R.color.colorPurpleDark
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(ImQuarantinedApp.getApplicationContext(), colorRes))
            snackbar.show()
        }
        else
            Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortSnackBar(
        view: View,
        message: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(ImQuarantinedApp.getApplicationContext(), R.color.colorPurpleDark))
            snackbar.show()
        }
        else
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
    }
}