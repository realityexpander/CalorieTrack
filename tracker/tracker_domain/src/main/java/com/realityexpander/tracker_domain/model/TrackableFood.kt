package com.realityexpander.tracker_domain.model


// Everything we need to show in the UI (for presentation layer)
data class TrackableFood(
    val name: String,
    val imageUrl: String?,

    val carbPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int,
    val caloriesPer100g: Int,
)
