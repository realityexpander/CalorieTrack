package com.realityexpander.tracker_presentation.tracker_overview_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.core.R
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.sp
import com.realityexpander.tracker_presentation.components.NutrientInfo
import com.realityexpander.tracker_presentation.components.UnitDisplay
import com.realityexpander.tracker_presentation.tracker_overview_screen.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Breakfast, Lunch, Dinner, Snack Images
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Meal Name
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3
                    )

                    // Expand/Collapse Icon
                    Icon(
                        imageVector = if (meal.isExpanded) {
                                Icons.Default.KeyboardArrowUp
                            } else
                                Icons.Default.KeyboardArrowDown,
                        contentDescription = if(meal.isExpanded) {
                                stringResource(id = R.string.collapse)
                            } else
                                stringResource(id = R.string.extend)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Calories kcal
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = R.string.kcal),
                        amountTextSize = 30.sp
                    )
                    Row {
                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.carbs),
                            amount = meal.carb,
                            unit = stringResource(id = R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = R.string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = R.string.grams)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}