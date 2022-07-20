package com.realityexpander.tracker_domain.model

sealed class MealOfDayType(val name: String) {
    object Breakfast : MealOfDayType("breakfast")
    object Lunch : MealOfDayType("lunch")
    object Dinner : MealOfDayType("dinner")
    object Snack : MealOfDayType("snack")


    companion object {
        fun fromString(name: String): MealOfDayType {
            return when (name) {
                "breakfast" -> Breakfast
                "lunch" -> Lunch
                "dinner" -> Dinner
                "snack" -> Snack
                else -> throw IllegalArgumentException("Unknown meal type: $name")
            }
        }
    }
}
