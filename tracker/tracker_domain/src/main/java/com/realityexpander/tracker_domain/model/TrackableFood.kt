package com.realityexpander.tracker_domain.model


// Everything we need to show in the UI
data class TrackableFood(
    val name: String,
    val imageUrl: String?,

    val carbsPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int,
    val caloriesPer100g: Int,
)
