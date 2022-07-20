package com.realityexpander.tracker_presentation.tracker_overview_screen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.realityexpander.core_ui.CarbColor
import com.realityexpander.core_ui.FatColor
import com.realityexpander.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carb: Int,
    fat: Int,
    protein: Int,
    calories: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier,
) {

    val background = MaterialTheme.colors.background
    val caloriesExceededColor = MaterialTheme.colors.error

    // set up animations for the bars
    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val exceedRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = carb) {
        carbWidthRatio.animateTo(
            (carb * 4f) / caloriesGoal
        )
    }
    LaunchedEffect(key1 = fat) {
        carbWidthRatio.animateTo(
            (fat * 4f) / caloriesGoal
        )
    }
    LaunchedEffect(key1 = protein) {
        carbWidthRatio.animateTo(
            (protein * 4f) / caloriesGoal
        )
    }
    LaunchedEffect(key1 = calories) {
        exceedRatio.animateTo(
            if(caloriesGoal > 0)
                (calories/caloriesGoal).toFloat()
            else
                0f
        )
    }

    Canvas(modifier = modifier) {
       if(calories <= caloriesGoal) {
           val carbsWidth = carbWidthRatio.value * size.width
           val fatsWidth = fatWidthRatio.value * size.width
           val proteinsWidth = proteinWidthRatio.value * size.width

           // Background
           drawRoundRect(
               color = background,
               size = size,
               cornerRadius = CornerRadius(100f)
           )

           // Fat
           drawRoundRect(
               color = FatColor,
               size = Size(carbsWidth + proteinsWidth + fatsWidth, size.height),
               cornerRadius = CornerRadius(100f)
           )

           // Protein
           drawRoundRect(
               color = ProteinColor,
               size = Size(carbsWidth + proteinsWidth, size.height),
               cornerRadius = CornerRadius(100f)
           )

           // Carb
           drawRoundRect(
               color = CarbColor,
               size = Size(carbsWidth, size.height),
               cornerRadius = CornerRadius(100f)
           )
       } else {
          // Exceeded calories
          val exceedWidth = exceedRatio.value * size.width

          drawRoundRect(
            color = caloriesExceededColor,
            size = Size(exceedWidth, size.height),
            cornerRadius = CornerRadius(100f)
          )
       }
    }
}