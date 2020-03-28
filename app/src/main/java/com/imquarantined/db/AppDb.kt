package com.imquarantined.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imquarantined.ImQuarantinedApp
import com.imquarantined.R
import com.imquarantined.data.LocationEntity


/* Created by ashiq.buet16 **/


@TypeConverters(
    DateConverter::class
)
@Database(
    entities = [
        LocationEntity::class
    ],
    version = DbConstants.DB_VERSION,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun locationsDao(): LocationDao

    companion object {

        private lateinit var INSTANCE: AppDb

        @Synchronized
        fun initDb(context: Context) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context, AppDb::class.java, context.getString(R.string.db_name))
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance(): AppDb {
            if(!::INSTANCE.isInitialized) initDb(ImQuarantinedApp.getApplicationContext())

            return INSTANCE
        }
    }
}