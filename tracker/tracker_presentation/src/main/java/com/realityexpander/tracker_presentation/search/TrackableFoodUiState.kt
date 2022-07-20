package com.realityexpander.tracker_presentation.search

import com.realityexpander.tracker_domain.model.TrackableFood


// Kept only for UI purposes, includes the expanded and amount of the food
data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)
