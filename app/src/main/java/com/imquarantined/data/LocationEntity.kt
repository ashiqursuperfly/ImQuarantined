package com.imquarantined.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.imquarantined.db.DbConstants
import java.util.*

/* Created by ashiq.buet16 **/

@Entity(tableName = DbConstants.LocationsTable.TABLE_NAME)
data class LocationEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.LocationsTable.COL_ID)
    var id: Long? = null,
    @SerializedName("lat")
    @ColumnInfo(name = DbConstants.LocationsTable.COL_LAT)
    var latitude: Double = 0.0,
    @SerializedName("long")
    @ColumnInfo(name = DbConstants.LocationsTable.COL_LONG)
    var longitude: Double = 0.0,
    @SerializedName("alti")
    @ColumnInfo(name = DbConstants.LocationsTable.COL_ALTI)
    var altitude: Double = 0.0,
    @SerializedName("date_time")
    @ColumnInfo(name= DbConstants.LocationsTable.COL_DATE_TIME)
    var dateTime: Date = Date()
)