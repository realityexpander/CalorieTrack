package com.realityexpander.calorietrack.repository

import com.realityexpander.tracker_domain.model.TrackableFood
import com.realityexpander.tracker_domain.model.TrackedFood
import com.realityexpander.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import kotlin.random.Random


// Use fakes for repositories
// Use mockks for other types of tests

class TrackerRepositoryFake: TrackerRepository {

    var shouldReturnError = false

    private val trackedFoods = mutableListOf<TrackedFood>()
    var searchResults = listOf<TrackableFood>()

    private val getFoodsForDateFlow =
        MutableSharedFlow<List<TrackedFood>>(replay = 1)  // replay (cache) = 1 to make sure the new collectors get the latest value

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> {
        if(shouldReturnError) {
            return Result.failure(Exception("Error"))
        }

        return Result.success(searchResults)
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackedFoods.add(food.copy(id = Random.nextInt()))
        getFoodsForDateFlow.emit(trackedFoods)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackedFoods.remove(food)
        getFoodsForDateFlow.emit(trackedFoods)
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return getFoodsForDateFlow
    }
}