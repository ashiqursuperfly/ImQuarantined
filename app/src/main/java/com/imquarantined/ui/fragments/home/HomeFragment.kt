package com.imquarantined.ui.fragments.home

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.IdpResponse
import com.imquarantined.BuildConfig
import com.imquarantined.R
import com.imquarantined.data.Const
import com.imquarantined.data.LocationData
import com.imquarantined.ui.base.BaseFragment
import com.imquarantined.util.helper.*
import com.imquarantined.util.helper.Toaster.showLongToast
import com.imquarantined.util.helper.Toaster.showToast
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import java.util.*

class HomeFragment : BaseFragment(),LocationListener {

    private val mHomeViewModel: HomeViewModel by viewModels()
    private var gpsUtil = GPSUtil()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun afterOnViewCreated() {
        observeData()
    }

    private fun observeData() {
        mHomeViewModel.mLoginLiveData.observe(this, Observer {
            if (it) {
                showToast("Welcome\n ${PrefUtil.get(Const.PrefProp.LOGIN_TOKEN, "-1")}")
                DialogUtil.hideLoader()
                setupLocationTracking()
            }
        })

    }

    private fun checkAuthentication() {
        //TODO: check isSigned in using sharedPref
        if (FirebaseAuthUtil.getUser() == null)
            startActivityForResult(FirebaseAuthUtil.getIntent(), Const.RequestCode.FIREBASE_AUTH)
        else {
            showToast("Already Logged In.")
            setupLocationTracking()
        }
    }

    private fun setupLocationTracking() {
        PermissionsUtil.requestPermission(requireContext(),requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION, getString(R.string.denied_location_permission))

        if(PermissionsUtil.isPermissionAllowed(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)){
            if(enableGPS()){
                gpsUtil.findLocation(requireActivity(),this)
            }
        } else {
            showLongToast(getString(R.string.denied_location_permission))
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
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
                                activity?.finish()
                            }
                        }
                    )
                val alertDialog = builder.create()
                alertDialog.show()

            return false
        }

        return true
    }

    private fun setupBarometer(){
        val isSensorSet = SensorUtil.setSensor(Sensor.TYPE_PRESSURE, object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val sensorData = sensorEvent.values
                mHomeViewModel.atmosphericPressure = sensorData[0]
            }
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }, requireActivity())

        if (!isSensorSet) {
            Timber.i("Device doesn't support barometer. Using Standard Pressure")
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthentication()
        setupBarometer()
        calibrateSensors()
    }

    private fun calibrateSensors() {
        val task = CustomTimerTask()
        task.setCallback(object: CustomTimerTask.TimerCallback {
            override fun onTime() {
                activity?.runOnUiThread {  mHomeViewModel.isCalibrationDone = true }
            }
        })

        val n = Const.Misc.LocationRequestPeriodMillis*Const.Misc.CalibrationPoints
        val timer = Timer()
        showLongToast("Calibrating ....")
        mHomeViewModel.isCalibrationDone = false
        timer.schedule(task, n)
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

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            val alt =SensorManager.getAltitude(
                SensorUtil.getSealevelPressure(
                    location.altitude.toFloat(),
                    mHomeViewModel.atmosphericPressure
                ),
                mHomeViewModel.atmosphericPressure
            )

            mHomeViewModel.mLocationData = LocationData(location.latitude, location.longitude, alt.toDouble())
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }


}