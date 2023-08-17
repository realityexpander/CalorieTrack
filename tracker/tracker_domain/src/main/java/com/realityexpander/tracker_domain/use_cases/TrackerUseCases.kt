package com.realityexpander.tracker_domain.use_cases

// Wrapper class for the use cases, for easier injecting into the constructor
data class TrackerUseCases(
    val addTrackedFood: AddTrackedFood,
    val searchFood: SearchFood,
    val getFoodsForDate: GetFoodsForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients,
)
