package com.realityexpander.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.domain.use_case.FilterKeepDigits
import com.realityexpander.core.util.UiEvent
import com.realityexpander.core.util.UiText
import com.realityexpander.tracker_domain.use_cases.TrackerUseCases
import com.realityexpander.tracker_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterKeepDigits
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }
            is SearchEvent.OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map { foodUiState ->
                        if(foodUiState.food == event.food) {
                            foodUiState.copy(amount = filterOutDigits(event.amount))
                        } else
                            foodUiState
                    }
                )
            }
            is SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map { foodUiState ->
                        if(foodUiState.food == event.food) {
                            foodUiState.copy(isExpanded = !foodUiState.isExpanded)
                        } else
                            foodUiState
                    }
                )
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }
            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFood = emptyList()
            )

            trackerUseCases
                .searchFood(state.query)
                .onSuccess { foods ->
                    state = state.copy(
                        // map the trackable food to the ui state of the trackable foods
                        trackableFood = foods.map { trackableFood ->
                            TrackableFoodUiState(trackableFood)
                        },
                        isSearching = false,
                        query = ""  // reset the query
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFood.find { foodUiState ->
                foodUiState.food == event.food
            }

            trackerUseCases.addTrackedFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealOfDayType = event.mealOfDayType,
                date = event.date
            )

            _uiEvent.send(UiEvent.NavigateUp) // pop back to previous screen
        }
    }
}




























