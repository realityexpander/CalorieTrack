package com.realityexpander.tracker_data.local

import androidx.room.*
import com.realityexpander.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

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
    fun getFoodsForDate(dayOfMonth: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}