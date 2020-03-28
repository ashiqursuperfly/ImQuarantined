package com.imquarantined.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.imquarantined.data.LocationEntity


/* Created by ashiq.buet16 **/

@Dao
interface LocationDao {
    @Query("SELECT * FROM " + DbConstants.LocationsTable.TABLE_NAME)
    fun getLocationsLiveData(): LiveData<List<LocationEntity>>

    @Query("SELECT * FROM " + DbConstants.LocationsTable.TABLE_NAME)
    fun getLocationsData(): List<LocationEntity>


    @Query("SELECT COUNT(" + DbConstants.LocationsTable.COL_ID + ") FROM " + DbConstants.LocationsTable.TABLE_NAME)
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationEntity): Long

    @Insert
    fun insertAll(locations: List<LocationEntity>): LongArray

    @Insert
    fun insertAll(vararg locations: LocationEntity): LongArray

    @Update
    fun update(location: LocationEntity): Int

    @Delete
    fun delete(location: LocationEntity): Int

    @Query("DELETE FROM " + DbConstants.LocationsTable.TABLE_NAME)
    fun deleteAll()
}
