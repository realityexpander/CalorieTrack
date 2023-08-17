package com.realityexpander.tracker_data.mappers

import com.plcoding.tracker_data.remote.dto.ProductDto
import com.realityexpander.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun ProductDto.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,

//        carbsPer100g = nutriments.carbohydrates100g.roundToInt(),
//        fatPer100g = nutriments.fat100g.roundToInt(),
//        proteinPer100g = nutriments.proteins100g.roundToInt(),
//        caloriesPer100g = nutriments.energyKcal100g.roundToInt(),

        carbPer100g     = (nutriments.carbohydratesg).roundToInt(),
        fatPer100g      = (nutriments.fatsg).roundToInt(),
        proteinPer100g  = (nutriments.proteinsg).roundToInt(),
        caloriesPer100g = (nutriments.energyKcalg).roundToInt(),
    )
}