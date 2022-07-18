package com.realityexpander.calorietrack.navigation

import androidx.navigation.NavController
import com.realityexpander.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)  // navigate to the route
}