package com.imquarantined.util.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener

/* Created by ashiq.buet16 **/

object PermissionsUtil {

    fun requestPermission(
        context: Context,
        activity: Activity,
        permission: String,
        listener: PermissionListener) {

        if (!isPermissionAllowed(context,permission)) {
            Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(listener).check()
        }
    }

    fun isPermissionAllowed(context: Context, permission: String): Boolean {
        val pm: PackageManager = context.packageManager
        val hasPermission = pm.checkPermission(
            permission,
            context.packageName
        )
        // Timber.d("Permission Status:$hasPermission")
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }

    fun isPermissionDenied(context: Context, permission: String): Boolean {
        val pm: PackageManager = context.packageManager
        val hasPermission = pm.checkPermission(
            permission,
            context.packageName
        )
        return hasPermission == PackageManager.PERMISSION_DENIED
    }

}