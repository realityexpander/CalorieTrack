package com.realityexpander.onboarding_presentation.welcome_screen.height_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.realityexpander.core.R
import com.realityexpander.core.util.UiEvent
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.onboarding_presentation.welcome_screen.age_screen.HeightViewModel
import com.realityexpander.onboarding_presentation.welcome_screen.components.ActionButton
import com.realityexpander.onboarding_presentation.welcome_screen.components.UnitTextField
import kotlinx.coroutines.flow.collect

@Composable
fun HeightScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HeightViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()  // fill the whole screen
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            
            UnitTextField(
                initialValue = viewModel.height,
                onValueChange = viewModel::onHeightEnter,
                units = stringResource(
                id = R.string.inches)
            )

        }

        // Nav to Next screen
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)  // align to bottom end of the screen
        )
    }

}