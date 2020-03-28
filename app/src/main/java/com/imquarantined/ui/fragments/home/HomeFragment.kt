package com.imquarantined.ui.fragments.home

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.IdpResponse
import com.imquarantined.BuildConfig
import com.imquarantined.R
import com.imquarantined.data.Const
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.ui.service.Actions
import com.imquarantined.ui.service.EndlessService
import com.imquarantined.ui.service.ServiceState
import com.imquarantined.ui.service.getServiceState
import com.imquarantined.util.helper.*
import com.imquarantined.util.helper.Toaster.showLongToast
import com.imquarantined.util.helper.Toaster.showToast
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import timber.log.Timber

class HomeFragment : BaseFragment(){

    private val mHomeViewModel: HomeViewModel by viewModels()
    private var gpsUtil = GPSUtil()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun afterOnViewCreated() {
        observeData()
        if(!gpsUtil.isGpsEnabled(requireContext()))actionOnService(Actions.STOP)
        val thread = Thread(object: Runnable{
            override fun run() {
                while (!gpsUtil.isGpsEnabled(requireContext()));
                actionOnService(Actions.START)
            }

        })
        thread.start()
    }

    private fun observeData() {
        mHomeViewModel.mLoginLiveData.observe(this, Observer {
            if (it) {
                showToast("Welcome\n ${PrefUtil.get(Const.PrefProp.LOGIN_TOKEN, "-1")}")
                DialogUtil.hideLoader()
                initLocationTracking()
            }
        })
    }

    private fun checkAuthenticationAndStartLocationTracking() {

        if (!mHomeViewModel.isUserLoggedIn())
            startActivityForResult(FirebaseAuthUtil.getIntent(), Const.RequestCode.FIREBASE_AUTH)
        else {
            // showToast("Already Logged In.")
            initLocationTracking()
        }
    }

    private fun initLocationTracking() {

        if(PermissionsUtil.isPermissionAllowed(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)){
            enableGPS()
        }
        else {
            PermissionsUtil.requestPermission(
                requireContext(),
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION,
                object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        showToast("Permission Granted")
                        enableGPS()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        showLongToast(getString(R.string.select_permissions_and_allow))
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                            )
                        )
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {

                    }
                }
            )
        }

    }

    private fun enableGPS(): Boolean {
        if (!gpsUtil.isGpsEnabled(requireActivity())) {
                val builder = AlertDialog.Builder(requireActivity())

                builder.setMessage(getString(R.string.enable_gps_to_proceed)).setCancelable(false)
                    .setPositiveButton(
                        "YES", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                activity?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            }
                        }
                    )
                    .setNegativeButton(
                        "NO",  object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog?.dismiss()
                                showToast(getString(R.string.enable_gps_to_proceed))
                                enableGPS()
                            }
                        }
                    )
                val alertDialog = builder.create()
                alertDialog.show()

            return false
        }

        return true
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(requireContext()) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(activity, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Timber.i("Starting the service in >=26 Mode")
                activity?.startForegroundService(it)
                return
            }
            Timber.i("Starting the service in < 26 Mode")
            activity?.startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationAndStartLocationTracking()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showToast("Activity result")
        if (requestCode == Const.RequestCode.FIREBASE_AUTH) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                DialogUtil.showLoader(requireContext())

                FirebaseAuthUtil.getUser()?.getIdToken(true)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val idToken: String = task.result?.token ?: ""
                            Timber.i("Token$idToken")
                            mHomeViewModel.login(idToken)
                        } else {
                            showToast(task.exception?.message.toString())
                            Timber.e(task.exception?.message.toString())
                            mHomeViewModel.signOut()
                            DialogUtil.hideLoader()
                            activity?.finish()
                        }
                    }
            } else {
                if (response == null) {
                    showToast("Login Cancelled")
                }
                showToast("Login Failed ${response?.error?.message ?: ""}")
                activity?.finish()
            }
        }
    }


}