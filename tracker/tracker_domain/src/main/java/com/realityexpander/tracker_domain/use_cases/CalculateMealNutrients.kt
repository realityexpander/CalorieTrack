package com.realityexpander.tracker_domain.use_cases

import com.realityexpander.core.domain.model.ActivityLevel
import com.realityexpander.core.domain.model.Gender
import com.realityexpander.core.domain.model.GoalType
import com.realityexpander.core.domain.model.UserInfo
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences
) {
    data class MealNutrients(
        val protein: Int,
        val fat: Int,
        val carb: Int,
        val calories: Int,
        val mealOfDayType: MealOfDayType
    )

    data class Result(
        val carbGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,

        val totalCarb: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,

        val mealNutrients: Map<MealOfDayType, MealNutrients>
    )

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods
            .groupBy{ trackedFood ->
                trackedFood.mealOfDayType
            }
            .mapValues { entry ->
                val mealType = entry.key
                val foods = entry.value

                MealNutrients(
                    carb = foods.sumOf { food -> food.carbs },
                    fat = foods.sumOf { food -> food.fat },
                    protein = foods.sumOf { food -> food.protein },
                    calories = foods.sumOf { food -> food.calories },
                    mealOfDayType = mealType
                )

            }

        val totalCarbs = allNutrients.values.sumOf { it.carb }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val calorieGoal = dailyCalorieRequirement(userInfo)
        val carbGoal = (calorieGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (calorieGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (calorieGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbGoal = carbGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = calorieGoal,
            totalCarb = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )

    }

    // basal metabolic rate
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f *
                        (userInfo.weight * 0.4535924) +  // lb to kg
                        5f *
                        (userInfo.height * 2.54) // in to cm
                        - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (65.09f + 9.56f *
                        (userInfo.weight * 0.4535924) + // lb to kg
                        1.84f *
                        (userInfo.height * 2.54) // in to cm
                        - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCalorieRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val calorieExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.MaintainWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + calorieExtra).roundToInt()
    }


}