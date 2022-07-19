package com.realityexpander.onboarding_presentation.age_screen

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
import com.realityexpander.core.domain.use_case.FilterKeepDigits
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterKeepDigits: FilterKeepDigits // use_case
): ViewModel() {
    var age by mutableStateOf<String>("18")
        private set

    // Channels are events sent only once
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if(age.length <= 3) {
            this.age = filterKeepDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageInt = age.toIntOrNull() ?: run {
                _uiEvent.send(UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.error_age_cant_be_empty)
                ))
                return@launch
            }

            preferences.saveAge(ageInt)
            _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))
        }
    }
}
