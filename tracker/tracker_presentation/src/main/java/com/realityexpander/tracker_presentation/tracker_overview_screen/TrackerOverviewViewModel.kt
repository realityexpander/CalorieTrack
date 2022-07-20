package com.realityexpander.tracker_presentation.tracker_overview_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.navigation.Route
import com.realityexpander.core.util.UiEvent
import com.realityexpander.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
): ViewModel() {

    var state by mutableStateOf(TrackerOverviewState()) // observed by composables
        private set

    // Channels are hot, and do not retain state.
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.onAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.SEARCH +
                                    "/${event.meal.mealType.name}" +
                                    "/${state.date.dayOfMonth}" +
                                    "/${state.date.monthValue}" +
                                    "/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)

                    refreshFoodsData()
                }
            }
            TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoodsData()
            }
            TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoodsData()
            }
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    mealsOfDay = state.mealsOfDay.map { meal ->
                        if(meal.name == event.meal.name) {
                            return@map meal.copy(isExpanded = !meal.isExpanded)
                        }

                        meal
                    }
                )
            }

        }
    }

    private fun refreshFoodsData() {
        getFoodsForDateJob?.cancel()

        getFoodsForDateJob = trackerUseCases
            .getFoodsForDate(state.date)
            .onEach { foods ->
                val nutrientResult = trackerUseCases.calculateMealNutrients(foods)

                state = state.copy(
                    totalCarb = nutrientResult.totalCarb,
                    totalFat = nutrientResult.totalFat,
                    totalProtein = nutrientResult.totalProtein,
                    totalCalories = nutrientResult.totalCalories,
                    carbGoal = nutrientResult.carbGoal,
                    fatGoal = nutrientResult.fatGoal,
                    proteinGoal = nutrientResult.proteinGoal,
                    caloriesGoal = nutrientResult.caloriesGoal,
                    trackedFoods = foods,
                    mealsOfDay = state.mealsOfDay.map { meal ->
                        val nutrientsForMeal =
                            nutrientResult.mealNutrients[meal.mealType]
                                ?: return@map meal.copy(
                                    carb = 0,
                                    fat = 0,
                                    protein = 0,
                                    calories = 0
                                )

                        meal.copy(
                            carb = nutrientsForMeal.carb,
                            fat = nutrientsForMeal.fat,
                            protein = nutrientsForMeal.protein,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }

}




























