package com.realityexpander.tracker_presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.realityexpander.core.R
import com.realityexpander.core.util.UiEvent
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.tracker_domain.model.MealOfDayType
import com.realityexpander.tracker_presentation.search.components.SearchTextField
import com.realityexpander.tracker_presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalCoilApi::class) // for use in TrackableFoodItem
@ExperimentalComposeUiApi  // for LocalSoftwareKeyboardController
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealOfDayName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }
                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        // `Add MealOfDay` prompt
        Text(
            text = stringResource(id = R.string.add_meal, mealOfDayName),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        // Search for food
        SearchTextField(
            text = state.query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.OnQueryChange(it))
            },
            onSearch = {
                viewModel.onEvent(SearchEvent.OnSearch)
                keyboardController?.hide()
            },
            onFocusChanged = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
            },
            shouldShowHint = state.isHintVisible
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.trackableFoodItemUiStates) { foodItem ->
                TrackableFoodItem(
                    trackableFoodItemUiState = foodItem,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(foodItem.food))
                    },
                    onAmountChange = { amountStr ->
                        viewModel.onEvent(SearchEvent.OnAmountForFoodChange(
                            foodItem.food, amountStr
                        ))
                    },
                    onAddTrackedFood = {
                        viewModel.onEvent(
                            SearchEvent.OnAddTrackedFoodClick(
                                food = foodItem.food,
                                mealOfDayType = MealOfDayType.fromString(mealOfDayName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                        keyboardController?.hide()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Show progress indicator & no items found.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFoodItemUiStates.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}