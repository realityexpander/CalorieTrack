package com.realityexpander.tracker_domain.model

import java.time.LocalDate


// unified class from database and api to be used in the domain layer
data class TrackedFood(
    val id: Int? = null,

    val name: String,
    val imageUrl: String?,

    val mealOfDayType: MealOfDayType,
    val amount: Int, // in grams
    val calories: Int, // in kcal

    val carb: Int,
    val protein: Int,
    val fat: Int,

    val date: LocalDate
)
