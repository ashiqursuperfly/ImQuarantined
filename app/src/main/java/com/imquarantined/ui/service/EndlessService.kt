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
import com.imquarantined.data.LocationEntity
import com.imquarantined.db.AppDb
import com.imquarantined.ui.MainActivity
import com.imquarantined.util.api.ApiClient
import com.imquarantined.util.helper.*
import com.imquarantined.util.helper.Toaster.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EndlessService : Service(), CustomServiceTask {

    private val mLocationsDao = AppDb.getInstance().locationsDao()

    private lateinit var mPressureSensorListener: SensorEventListener
    private lateinit var mLocationListener: LocationListener
    private lateinit var mLastKnowLocation : LocationEntity

    private var gpsUtil = GPSUtil()
    var atmosphericPressure  = SensorManager.PRESSURE_STANDARD_ATMOSPHERE
    var isCalibrationDone = false

    var i=0L
    override fun backGroundWork() {
        if(gpsUtil.isGpsEnabled(this)){
            if(::mLastKnowLocation.isInitialized) {
                val locations = ArrayList(mLocationsDao.getLocationsData())
                locations.add(mLastKnowLocation)

                if(locations.size < 2)mLocationsDao.insert(mLastKnowLocation)
                else {
                    updateLocationsRemote(locations)
                }
                Timber.d("**BGWORK** Location $i: $mLastKnowLocation")
            }

        }
        else {
            LocalNotificationUtil.showNotification(
                this,
                getString(R.string.title_notification_gps_off),
                getString(R.string.body_notification_gps_off),
                Intent(this, MainActivity::class.java),
                Const.Notification.promptGpsOffWhileBgTaskChannelId,
                Const.Notification.promptGpsOffWhileBgTaskChannelName
            )
        }

    }

    override fun initBackGroundWork() {
        setupBarometer()
        calibrateSensors()
        setupLocationTracking()
    }

    override fun endBackGroundWork() {
        gpsUtil.unregisterLocationListener(this, mLocationListener)
        SensorUtil.unregisterListener(this, mPressureSensorListener)
    }

    private fun setupLocationTracking() {
        mLocationListener = object: LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    val alt = SensorManager.getAltitude(
                        SensorUtil.getSealevelPressure(location.altitude.toFloat(), atmosphericPressure),
                        atmosphericPressure
                    )

                    mLastKnowLocation = LocationEntity(latitude = location.latitude, longitude = location.longitude, altitude = alt.toDouble(), dateTime = Date(System.currentTimeMillis()))
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String?) {}

            override fun onProviderDisabled(provider: String?) {}

        }

        if(gpsUtil.isGpsEnabled(this)){
            gpsUtil.registerLocationListener(this,mLocationListener)
        }else {
            //This should never be called since, we only start the service when we are sure gps is enabled
            LocalNotificationUtil.showNotification(
                this,
                getString(R.string.title_notification_gps_off),
                getString(R.string.body_notification_gps_off),
                Intent(this, MainActivity::class.java),
                Const.Notification.promptGpsOffWhileBgTaskChannelId,
                Const.Notification.promptGpsOffWhileBgTaskChannelName
            )
        }
    }

    private fun setupBarometer(){

        mPressureSensorListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val sensorData = sensorEvent.values
                atmosphericPressure = sensorData[0]
            }
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }

        val isSensorSet = SensorUtil.setSensor(Sensor.TYPE_PRESSURE,mPressureSensorListener, this)

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

        val n = Const.Config.locationRequestPeriodMillis* Const.Config.calibrationPoints
        val timer = Timer()
        isCalibrationDone = false
        timer.schedule(task, n)
    }

    private fun updateLocationsRemote(items: ArrayList<LocationEntity>) {
        val token = PrefUtil.get(Const.PrefProp.LOGIN_TOKEN, "-1")
        if (token == "-1") {
            LocalNotificationUtil.showNotification(
                this,
                "Not logged in?",
                "This should never happen.",
                Intent(this, MainActivity::class.java),
                Const.Notification.promptLoginChannelId,
                Const.Notification.promptLoginChannelName
            )
            return
        }

        val jo = JSONObject()
        val ja = JSONArray()

        for(i in 0 until items.size){
            ja.put(i,jo.put(Const.Api.Params.POST.LAT, items[i].latitude))
            ja.put(i,jo.put(Const.Api.Params.POST.LONG, items[i].longitude))
            ja.put(i,jo.put(Const.Api.Params.POST.ALTI, items[i].altitude))
            var simpleDateFormat = SimpleDateFormat("dd/mm/yyyy hh:mm:ss", Locale.US)

            ja.put(i,jo.put(Const.Api.Params.POST.DATE_TIME, simpleDateFormat.format(items[i].dateTime)))
        }

        Timber.d("Updating Locations:\n $ja")

        val disposable = CompositeDisposable()
        val api = ApiClient.createCommonApiService()

        disposable.add(
            api.updateLocations(ja)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    DialogUtil.hideLoader()
                    if (it.isSuccess) {
                        //remove all from db except the latest(since the latest is not in db)
                        Timber.d("UpdateLocationsResponse: $it")
                        items.remove(items.last())
                        for (item in items) mLocationsDao.delete(item)

                    } else {
                        Timber.d("UpdateLocationsResponse: $it")
                        for (item in items) mLocationsDao.delete(item)
                        //TODO:Show notfication using failed at
                    }
                }, {
                    Timber.d("UpdateLocationsResponse: $it")
                    //push only latest location to db, since all the others must be in db anyway
                    mLocationsDao.insert(items.last())
                })
        )
    }

    /* The following code should NOT be changed */
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
        initBackGroundWork()
        val notification = createNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        endBackGroundWork()
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
                    backGroundWork()
                }
                delay(Const.Config.backgroundTaskPeriod)
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
        val notificationChannelId = Const.Notification.bgServiceChannelId

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                Const.Notification.bgServiceChannelName,
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = Const.Notification.bgServiceChannelName
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
            .setContentTitle(getString(R.string.title_notification_endless_bg_task))
            .setContentText(getString(R.string.body_notification_endless_bg_task))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }
}