package com.imquarantined.ui.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import com.imquarantined.R
import com.imquarantined.data.Const
import com.imquarantined.data.LocationData
import com.imquarantined.ui.MainActivity
import com.imquarantined.util.helper.CustomTimerTask
import com.imquarantined.util.helper.GPSUtil
import com.imquarantined.util.helper.SensorUtil
import com.imquarantined.util.helper.Toaster.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class EndlessService : Service(), LocationListener {

    private var gpsUtil = GPSUtil()
    var mLocationData : LocationData? = null
    var atmosphericPressure  = SensorManager.PRESSURE_STANDARD_ATMOSPHERE
    var isCalibrationDone = false

    var i=0
    private fun backgroundWork() {
        //TODO:
        Timber.d("Doing BG work: $i")
    }

    private fun setupLocationTracking() {
        if(gpsUtil.isGpsEnabled(this)){
            gpsUtil.findLocation(this,this)
        }else {
            //TODO: Record Error on local db or server
        }
    }

    private fun initBackgroundWork() {
        setupLocationTracking()
        setupBarometer()
        calibrateSensors()
    }

    private fun setupBarometer(){
        val isSensorSet = SensorUtil.setSensor(Sensor.TYPE_PRESSURE, object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val sensorData = sensorEvent.values
                atmosphericPressure = sensorData[0]
            }
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }, this)

        if (!isSensorSet) {
            Timber.i("Device doesn't support barometer. Using Standard Pressure")
        }
    }

    private fun calibrateSensors() {
        val task = CustomTimerTask()
        task.setCallback(object: CustomTimerTask.TimerCallback {
            override fun onTime() {
                  isCalibrationDone = true
            }
        })

        val n = Const.Misc.LocationRequestPeriodMillis* Const.Misc.CalibrationPoints
        val timer = Timer()
        isCalibrationDone = false
        timer.schedule(task, n)
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            val alt = SensorManager.getAltitude(
                SensorUtil.getSealevelPressure(
                    location.altitude.toFloat(),
                    atmosphericPressure
                ),
                atmosphericPressure
            )

            mLocationData = LocationData(location.latitude, location.longitude, alt.toDouble())
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

    /* The following code should never be changed */
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        Timber.i("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            Timber.i("using an intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else -> Timber.e("This should never happen. No action in the received intent")
            }
        } else {
            Timber.i(
                "with a null intent. It has been probably restarted by the system."
            )
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("The service has been created".toUpperCase(Locale.getDefault()))
        initBackgroundWork()
        val notification = createNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("The service has been destroyed".toUpperCase(Locale.getDefault()))
        showToast( "Service destroyed")
    }

    private fun startService() {
        if (isServiceStarted) return
        Timber.i("Starting the foreground service task")
        showToast( "Service starting its task")
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    backgroundWork()
                }
                delay(1 * 60 * 1000)
            }
            Timber.i("End of the loop for the service")
        }
    }

    private fun stopService() {
        Timber.i("Stopping the foreground service")
        showToast("Service stopping")
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            Timber.i("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("Endless Service")
            .setContentText("This is your favorite endless service working")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }
}