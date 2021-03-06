package com.realityexpander.tracker_data.mappers

import com.realityexpander.tracker_data.local.entity.TrackedFoodEntity
import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        id = id,
        name = name,
        imageUrl = imageUrl,

        carb = carbs,
        protein = protein,
        fat = fat,
        calories = calories,

        mealOfDayType = MealOfDayType.fromString(mealType),
        amount = amount,

        date = LocalDate.of(year, month, dayOfMonth)
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,

        carbs = carb,
        protein = protein,
        fat = fat,
        calories = calories,

        mealType = mealOfDayType.name,
        amount = amount,

        year = date.year,
        month = date.monthValue,
        dayOfMonth = date.dayOfMonth
    )
}