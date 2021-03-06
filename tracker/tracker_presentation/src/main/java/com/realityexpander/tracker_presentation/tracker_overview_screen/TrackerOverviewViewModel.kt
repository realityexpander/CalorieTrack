package com.realityexpander.tracker_presentation.tracker_overview_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.util.UiEvent
import com.realityexpander.tracker_domain.use_cases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
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
        refreshFoodsData()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)

                    refreshFoodsData()
                }
            }
            TrackerOverviewEvent.OnTodayClick -> {
                state = state.copy(
                    date = LocalDate.now()
                )
                refreshFoodsData()
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
            is TrackerOverviewEvent.OnToggleMealOfDayClick -> {
                state = state.copy(
                    mealsOfDay = state.mealsOfDay.map { mealOfDay ->

                        // Only expand the mealOfDay that was clicked.
                        if(mealOfDay.name == event.mealOfDay.name) {
                            return@map mealOfDay.copy(isExpanded = !mealOfDay.isExpanded)
                        }

                        mealOfDay
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
                val nutrientResult =
                    trackerUseCases.calculateMealNutrients(foods)

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
                    mealsOfDay = state.mealsOfDay.map { mealOfDay ->

                        val mealOfDayNutrient =
                            nutrientResult.mealOfDayNutrients[mealOfDay.mealOfDayType]
                                ?: return@map mealOfDay.copy(
                                    carb = 0,
                                    fat = 0,
                                    protein = 0,
                                    calories = 0
                                )

                        mealOfDay.copy(
                            carb = mealOfDayNutrient.carb,
                            fat = mealOfDayNutrient.fat,
                            protein = mealOfDayNutrient.protein,
                            calories = mealOfDayNutrient.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }

}




























