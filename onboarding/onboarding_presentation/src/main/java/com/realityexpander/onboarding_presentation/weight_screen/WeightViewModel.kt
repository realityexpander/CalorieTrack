package com.realityexpander.onboarding_presentation.welcome_screen.age_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.navigation.Route
import com.realityexpander.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.core.util.UiText
import com.realityexpander.core.R
import com.realityexpander.core.domain.use_case.FilterKeepDigitsAndDecimals
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterKeepDigitsAndDecimals: FilterKeepDigitsAndDecimals // use_case
): ViewModel() {
    var weight by mutableStateOf<String>("160.0")
        private set

    // Channels are events sent only once
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if(weight.length <= 5) {
            this.weight = filterKeepDigitsAndDecimals(weight)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightFloat = weight.toFloatOrNull() ?: run {
                _uiEvent.send(UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.error_weight_cant_be_empty)
                ))
                return@launch
            }

            preferences.saveWeight(weightFloat)
            _uiEvent.send(UiEvent.Navigate(Route.ACTIVITY_LEVEL))
        }
    }
}
