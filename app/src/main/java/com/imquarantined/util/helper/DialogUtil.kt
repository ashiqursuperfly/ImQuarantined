package com.imquarantined.util.helper

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.imquarantined.R
import com.imquarantined.databinding.LayoutProgressBarBinding

/* Created by ashiq.buet16 **/

object DialogUtil {
    private var mLoader: AlertDialog? = null

    fun showLoader(context: Context, cancelable: Boolean = false): AlertDialog? {

        val binding = DataBindingUtil.inflate<LayoutProgressBarBinding>(
            LayoutInflater.from(context), R.layout.layout_progress_bar, null, false
        )

        hideLoader()
        mLoader = AlertDialog.Builder(context, R.style.TransparentDialog)
            .setView(binding.root)
            .setCancelable(cancelable)
            .create()

        mLoader?.show()

        return mLoader
    }

    fun hideLoader() {
        try {
            mLoader?.dismiss()
        }catch (e: Exception){

        }
    }

    fun showAlertDialog(
        activity: Activity,
        title: String?,
        msg: String?,
        btnPosText: String?,
        btnNegText: String?,
        btnBgColor: Int,
        taskSuccess: DialogInterface.OnClickListener?,
        taskCancelled: DialogInterface.OnClickListener?
    ) {
        val alertDialog =
            AlertDialog.Builder(activity).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnPosText, taskSuccess)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, btnNegText, taskCancelled)
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setBackgroundColor(activity.resources.getColor(btnBgColor))
        }
        alertDialog.show()
    }

    fun createCustomDialog(context: Context, view: View, cancelable: Boolean = true): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(cancelable)
            .create()
    }
}