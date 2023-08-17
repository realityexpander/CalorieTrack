package com.realityexpander.tracker_presentation.tracker_overview_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.tracker_presentation.tracker_overview_screen.components.*
import com.realityexpander.core.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,  // mealOfDayName, dayOfMonth, month, year
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        // Header
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                },
                onTodayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnTodayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }

        // Meals
        items(state.mealsOfDay) { mealOfDay ->
            ExpandableMealOfDay(
                mealOfDay = mealOfDay,
                onToggleClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnToggleMealOfDayClick(mealOfDay))
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        // Does this tracked food belong to this mealOfDay?
                        val foods = state.trackedFoods.filter { food ->
                            food.mealOfDayType == mealOfDay.mealOfDayType
                        }

                        // List all the foods for this mealOfDay
                        foods.forEach { food ->
                            TrackedFoodItem(
                                trackedFood = food,
                                onDeleteClick = {
                                    viewModel.onEvent(
                                        TrackerOverviewEvent
                                            .OnDeleteTrackedFoodClick(food)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }

                        AddButton(
                            text = stringResource(
                                id = R.string.add_meal,
                                mealOfDay.name.asString(context)
                            ),
                            onClick = {
                                onNavigateToSearch(
                                    mealOfDay.mealOfDayType.name,
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}