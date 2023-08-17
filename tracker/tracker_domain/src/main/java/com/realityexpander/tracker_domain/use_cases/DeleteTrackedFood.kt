package com.realityexpander.tracker_domain.use_cases

import com.realityexpander.tracker_domain.model.TrackableFood
import com.realityexpander.tracker_domain.model.TrackedFood
import com.realityexpander.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(trackedFood: TrackedFood) {
        return repository.deleteTrackedFood(trackedFood)
    }

}