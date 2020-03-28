package com.imquarantined

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.imquarantined.db.AppDb
import timber.log.Timber

/* Created by ashiq.buet16 **/

class ImQuarantinedApp : MultiDexApplication() {

    init {
        sInstance = this
    }

    companion object {
        private lateinit var sInstance: ImQuarantinedApp

        fun getApplicationContext(): Context {
            return sInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        AppDb.initDb(this)
        plantTimber()
    }

    private fun plantTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return "Timber: "+super.createStackElementTag(element) +
                            " - ${element.methodName}() - Line:${element.lineNumber}"
                }
            })
            return
        }

        Timber.plant(CrashReportingTree())
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            FakeCrashLibrary.log(priority, tag, message)

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    //FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }

    class FakeCrashLibrary private constructor() {

        init {
            throw AssertionError("No instances.")
        }

        companion object {
            fun log(priority: Int, tag: String?, message: String) {

            }

            fun logWarning(t: Throwable) {

            }

            fun logError(t: Throwable) {

            }
        }
    }

}