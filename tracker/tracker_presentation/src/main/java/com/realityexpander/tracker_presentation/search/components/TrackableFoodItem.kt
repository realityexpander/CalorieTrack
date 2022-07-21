package com.realityexpander.tracker_presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.realityexpander.core.R
import com.realityexpander.core_ui.LocalSpacing
import com.realityexpander.tracker_presentation.components.NutrientInfo
import com.realityexpander.tracker_presentation.search.TrackableFoodItemUiState

//@OptIn(ExperimentalCoilApi::class)
//@Composable
//@Preview(showBackground = true)
//fun Preview(){
//    TrackableFoodItem(
//        TrackableFoodItemUiState(
//            TrackableFood(
//                name = "Apple",
//                imageUrl = "https://images.openfoodfacts.org/images/products/04124498/front_en.24.100.jpg",
//                carbsPer100g = 10,
//                proteinPer100g = 10,
//                fatPer100g = 10,
//                caloriesPer100g = 100
//            ),
//            isExpanded = true,
//            amount = "100"
//        ),
//        onClick = {},
//        onAmountChange = {},
//        onAddTrackedFood = {},
//        modifier = Modifier.fillMaxWidth()
//    )
//}

@ExperimentalCoilApi
@Composable
fun TrackableFoodItem(
    trackableFoodItemUiState: TrackableFoodItemUiState,
    onClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onAddTrackedFood: () -> Unit,
    modifier: Modifier = Modifier
) {
    val food = trackableFoodItemUiState.food
    val spacing = LocalSpacing.current

    // Card
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(end = spacing.spaceMedium)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f)
            ) {
                // Food product image
                Image(
                    painter = rememberImagePainter(
                        data = food.imageUrl,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_burger)
                            fallback(R.drawable.ic_burger)
                        }
                    ),
                    contentDescription = food.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp))
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                Column {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.h4,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Food product name & calories
                        Text(
                            text = stringResource(
                                id = R.string.kcal_per_100g,
                                food.caloriesPer100g
                            ),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1.5f)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))

                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.carbs),
                            amount = food.carbPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.protein),
                            amount = food.proteinPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            nutrientName = stringResource(id = R.string.fat),
                            amount = food.fatPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }

        // Amount input & Add to track list button
        AnimatedVisibility(visible = trackableFoodItemUiState.isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {

                    // Amount input
                    BasicTextField(
                        value = trackableFoodItemUiState.amount,
                        onValueChange = onAmountChange,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if(trackableFoodItemUiState.amount.isNotBlank()) {
                                    ImeAction.Done
                                } else
                                    ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onAddTrackedFood()
                                defaultKeyboardAction(ImeAction.Done)
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 0.5.dp,
                                color = MaterialTheme.colors.onSurface
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.spaceMedium)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

                    // grams
                    Text(
                        text = stringResource(id = R.string.grams),
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                }

                // Add to track list button
                IconButton(
                    onClick = onAddTrackedFood,
                    enabled = trackableFoodItemUiState.amount.isNotBlank()
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(id = R.string.track)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

                        Text(
                            text = stringResource(id = R.string.add),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.align(CenterVertically)
                        )
                    }
                }
            }
        }
    }
}