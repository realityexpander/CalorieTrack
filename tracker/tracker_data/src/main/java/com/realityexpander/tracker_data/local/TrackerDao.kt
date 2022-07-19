package com.realityexpander.tracker_data.local

import androidx.room.*
import com.realityexpander.tracker_data.local.entity.TrackedFoodEntity

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query(
    """
        SELECT * 
        FROM tracked_food 
        WHERE :dayOfMonth = dayOfMonth AND :month = month AND :year = year
    """
    )
    fun getFoodForDate(dayOfMonth: Int, month: Int, year: Int): List<TrackedFoodEntity>
}