package com.realityexpander.onboarding_presentation.welcome_screen.activity_level_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.realityexpander.core.util.UiEvent
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.core.R
import com.realityexpander.core.domain.model.ActivityLevel
import com.realityexpander.onboarding_presentation.welcome_screen.components.ActionButton
import com.realityexpander.onboarding_presentation.welcome_screen.components.SelectableButton
import kotlinx.coroutines.flow.collect

@Composable
fun ActivityLevelScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ActivityLevelViewModel = hiltViewModel(),
) {

    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
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
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {

                // Low button
                SelectableButton(text = stringResource(id = R.string.low),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Low,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onActivityLevelClick(ActivityLevel.Low)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                // Medium Button
                SelectableButton(text = stringResource(id = R.string.medium),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Medium,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onActivityLevelClick(ActivityLevel.Medium)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                // High Button
                SelectableButton(text = stringResource(id = R.string.high),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.High,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onActivityLevelClick(ActivityLevel.High)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
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