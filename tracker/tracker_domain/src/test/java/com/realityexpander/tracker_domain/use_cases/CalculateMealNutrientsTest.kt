package com.realityexpander.tracker_domain.use_cases

import com.google.common.truth.Truth.assertThat
import com.realityexpander.core.domain.model.ActivityLevel
import com.realityexpander.core.domain.model.Gender
import com.realityexpander.core.domain.model.GoalType
import com.realityexpander.core.domain.model.UserInfo
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 28,
            weight = 160f,
            height = 65f,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.MaintainWeight,
            proteinRatio = 0.4f,
            fatRatio = 0.3f,
            carbRatio = 0.3f
        )

        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Food $it",
                calories = Random.nextInt(2000),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                carb = Random.nextInt(100),
                mealOfDayType = MealOfDayType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack")[Random.nextInt(4)]
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
            )
        }

        val result = calculateMealNutrients(trackedFoods)

        val calculatedBreakfastCalories = result.mealOfDayNutrients.values
            .filter { it.mealOfDayType == MealOfDayType.Breakfast }
            .sumOf{ it.calories }
        val expectedBreakfastCalories = trackedFoods
            .filter { it.mealOfDayType == MealOfDayType.Breakfast }
            .sumOf{ it.calories }

        assertThat(calculatedBreakfastCalories).isEqualTo(expectedBreakfastCalories)
    }

    @Test
    fun `carbs for dinner properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "Food $it",
                calories = Random.nextInt(2000),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                carb = Random.nextInt(100),
                mealOfDayType = MealOfDayType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack")[Random.nextInt(4)]
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
            )
        }

        val result = calculateMealNutrients(trackedFoods)

        val calculatedDinnerCarbs = result.mealOfDayNutrients.values
            .filter { it.mealOfDayType == MealOfDayType.Dinner }
            .sumOf{ it.carb }
        val expectedDinnerCarbs = trackedFoods
            .filter { it.mealOfDayType == MealOfDayType.Dinner }
            .sumOf{ it.carb }

        assertThat(calculatedDinnerCarbs).isEqualTo(expectedDinnerCarbs)
    }

}

