package com.realityexpander.tracker_presentation.search

import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(
            val query: String
        ) : SearchEvent()

    object OnSearch : SearchEvent()

    data class OnToggleTrackableFood(
            val food: TrackableFood
        ) : SearchEvent()

    data class OnAmountForFoodChange(
            val food: TrackableFood,
            val amount: String
        ) : SearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealOfDayType: MealOfDayType,
        val date: LocalDate
        ): SearchEvent()

    data class OnSearchFocusChange(
            val isFocused: Boolean
        ): SearchEvent()
}
