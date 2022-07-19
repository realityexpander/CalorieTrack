package com.realityexpander.tracker_domain.use_cases

import com.realityexpander.tracker_domain.model.TrackedFood

// Wrapper class for the use cases, for easier injecting into the constructor
data class TrackerUseCases(
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getFoodsForDate: GetFoodsForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients,
)
