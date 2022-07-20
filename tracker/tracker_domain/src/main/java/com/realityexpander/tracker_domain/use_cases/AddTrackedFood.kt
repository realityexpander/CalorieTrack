package com.realityexpander.tracker_domain.use_cases

import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_domain.model.TrackableFood
import com.realityexpander.tracker_domain.model.TrackedFood
import com.realityexpander.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class AddTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealOfDayType: MealOfDayType,
        date: LocalDate
    ) {
        return repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                imageUrl = food.imageUrl,
                carbs    = ((food.carbsPer100g/100f) * amount).roundToInt(),
                fat      = ((food.fatPer100g/100f) * amount).roundToInt(),
                protein  = ((food.proteinPer100g/100f) * amount).roundToInt(),
                calories = ((food.caloriesPer100g/100f) * amount).roundToInt(),
                amount = amount,
                mealOfDayType = mealOfDayType,
                date = date
            )
        )
    }

}