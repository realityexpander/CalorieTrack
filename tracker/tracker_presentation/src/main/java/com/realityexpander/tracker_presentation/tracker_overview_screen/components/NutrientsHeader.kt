package com.realityexpander.tracker_presentation.tracker_overview_screen.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.tracker_presentation.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.realityexpander.core_ui.CarbColor
import com.realityexpander.core_ui.FatColor
import com.realityexpander.core_ui.ProteinColor
import com.realityexpander.tracker_presentation.components.UnitDisplay
import com.realityexpander.tracker_presentation.tracker_overview_screen.TrackerOverviewState

@Composable
fun NutrientsHeader(
    state: TrackerOverviewState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val animatedCalorieCount = animateIntAsState(
        targetValue = state.totalCalories
    )
    val animatedCalorieGoal = animateIntAsState(
        targetValue = state.caloriesGoal
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colors.primary)
            .padding(
                horizontal = spacing.spaceLarge,
                vertical = spacing.spaceExtraLarge
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Current Calorie Count
            UnitDisplay(
                amount = animatedCalorieCount.value,
                unit = stringResource(id = R.string.kcal),
                amountColor = MaterialTheme.colors.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)
            )

            // Your Goal
            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary
                )
                UnitDisplay(
                    amount = animatedCalorieGoal.value,
                    unit = stringResource(id = R.string.kcal),
                    amountColor = MaterialTheme.colors.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colors.onPrimary,
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        NutrientsBar(
            carb = state.totalCarb,
            protein = state.totalProtein,
            fat = state.totalFat,
            calories = state.totalCalories,
            caloriesGoal = state.caloriesGoal,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientCircleInfo(
                value = state.totalCarb,
                goal = state.carbGoal,
                nutrientName = stringResource(id = R.string.carbs),
                color = CarbColor,
                modifier = Modifier.size(90.dp)
            )
            NutrientCircleInfo(
                value = state.totalProtein,
                goal = state.proteinGoal,
                nutrientName = stringResource(id = R.string.protein),
                color = ProteinColor,
                modifier = Modifier.size(90.dp)
            )
            NutrientCircleInfo(
                value = state.totalFat,
                goal = state.fatGoal,
                nutrientName = stringResource(id = R.string.fat),
                color = FatColor,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}