package com.realityexpander.onboarding_presentation.welcome_screen.goal_type_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.navigation.Route
import com.realityexpander.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.domain.model.GoalType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalTypeViewModel @Inject constructor(
    private val preferences: Preferences
): ViewModel() {
    var selectedGoalType by mutableStateOf<GoalType>(GoalType.LoseWeight)
        private set

    // Channels are events sent only once
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeClick(goalType: GoalType) {
        selectedGoalType = goalType
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalType)
            _uiEvent.send(UiEvent.Navigate(Route.NUTRIENT_GOAL))
        }
    }
}