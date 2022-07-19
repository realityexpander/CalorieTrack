package com.realityexpander.tracker_data.repository

import androidx.lifecycle.Transformations.map
import com.realityexpander.tracker_data.local.TrackerDao
import com.realityexpander.tracker_data.mappers.toTrackableFood
import com.realityexpander.tracker_data.mappers.toTrackedFood
import com.realityexpander.tracker_data.mappers.toTrackedFoodEntity
import com.realityexpander.tracker_data.remote.OpenFoodApi
import com.realityexpander.tracker_domain.model.TrackableFood
import com.realityexpander.tracker_domain.model.TrackedFood
import com.realityexpander.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
): TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query, page, pageSize)

            Result.success(
                searchDto.products.mapNotNull {
                    it.toTrackableFood()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            dayOfMonth = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year,
        ).map { entities ->
            entities.map { entity ->
                entity.toTrackedFood()
            }
        }
    }
}