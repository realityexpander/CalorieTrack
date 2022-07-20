package com.realityexpander.tracker_presentation.tracker_overview_screen

import com.realityexpander.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent{
    object OnNextDayClick: TrackerOverviewEvent()
    object OnPreviousDayClick: TrackerOverviewEvent()
    object OnTodayClick: TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood): TrackerOverviewEvent()
    data class onAddFoodClick(val meal: Meal): TrackerOverviewEvent()
}
