package com.realityexpander.onboarding_presentation.nutrient_goal_screen

sealed class NutrientGoalEvent {
    data class OnCarbRatioEnter(val carbRatio: String) : NutrientGoalEvent()
    data class OnFatRatioEnter(val fatRatio: String) : NutrientGoalEvent()
    data class OnProteinRatioEnter(val proteinRatio: String) : NutrientGoalEvent()
    object OnNextClick : NutrientGoalEvent()
}
