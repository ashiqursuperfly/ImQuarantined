package com.imquarantined.db


/* Created by ashiq.buet16 **/

object DbConstants  {

    const val DB_VERSION = 1
    object LocationsTable {
        const val TABLE_NAME = "locations"
        const val COL_ID = "id"
        const val COL_LAT = "latitude"
        const val COL_LONG = "longitude"
        const val COL_ALTI = "altitude"
        const val COL_DATE_TIME = "date_time"
    }

}