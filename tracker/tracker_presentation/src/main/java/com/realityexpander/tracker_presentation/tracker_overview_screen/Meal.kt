package com.realityexpander.tracker_presentation.tracker_overview_screen

import androidx.annotation.DrawableRes
import com.realityexpander.core.util.UiText
import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.core.R


// UI state only for the UI for this screen (not needed anywhere else)
data class Meal(
    val name: UiText, // meal type: breakfast, lunch, dinner, snack
    @DrawableRes val drawableRes: Int,
    val mealOfDayType: MealOfDayType,
    val carb: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)


val defaultMeals = listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_breakfast,
        mealOfDayType = MealOfDayType.Breakfast,
        carb = 0,
        protein = 0,
        fat = 0,
        calories = 0
    ),
    Meal(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        mealOfDayType = MealOfDayType.Breakfast,
        carb = 0,
        protein = 0,
        fat = 0,
        calories = 0
    ),
    Meal(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        mealOfDayType = MealOfDayType.Breakfast,
        carb = 0,
        protein = 0,
        fat = 0,
        calories = 0
    ),
    Meal(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        mealOfDayType = MealOfDayType.Breakfast,
        carb = 0,
        protein = 0,
        fat = 0,
        calories = 0
    ),
)