package com.imquarantined.util.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import timber.log.Timber

/* Created by ashiq.buet16 **/

object PermissionsUtil {

    fun requestCameraPermission(context: Context, activity: Activity) {

        if (!checkCameraPermission(context)) {
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        Toaster.showToast("Permission Granted")
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        Toaster.showToast("Please enable camera permission.")
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {

                    }
                }).check()
        }
    }

    fun checkCameraPermission(context: Context): Boolean {
        val pm: PackageManager = context.packageManager
        val hasPermission = pm.checkPermission(
            Manifest.permission.CAMERA,
            context.packageName
        )
        Timber.d("Permission Status:$hasPermission")
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }

}