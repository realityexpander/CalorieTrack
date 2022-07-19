package com.realityexpander.onboarding_presentation.nutrient_goal_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.domain.use_case.FilterKeepDigits
import com.realityexpander.core.navigation.Route
import com.realityexpander.core.util.UiEvent
import com.realityexpander.onboarding_domain.use_cases.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterKeepDigits: FilterKeepDigits,    // use_case
    private val validateNutrients: ValidateNutrients,  // use_case
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    // Channels contain events sent only once
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(
                    carbRatioInt = filterKeepDigits(event.carbRatio)
                )
            }
            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(
                    fatRatioInt = filterKeepDigits(event.fatRatio)
                )
            }
            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(
                    proteinRatioInt = filterKeepDigits(event.proteinRatio)
                )
            }
            NutrientGoalEvent.OnNextClick -> {

                // Validate the fields
                when (
                    val result = validateNutrients(
                        state.carbRatioInt,
                        state.fatRatioInt,
                        state.proteinRatioInt
                    )) {
                    is ValidateNutrients.Result.Success -> {

                        // Save the values in the preferences
                        preferences.saveCarbRatio(result.carbRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        preferences.saveProteinRatio(result.proteinRatio)

                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }
                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.errorMessage))
                        }
                        return@onEvent
                    }
                }


            }
        }
    }
}