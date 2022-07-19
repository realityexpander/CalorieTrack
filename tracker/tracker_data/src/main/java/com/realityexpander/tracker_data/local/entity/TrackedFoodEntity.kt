package com.realityexpander.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracked_food")
data class TrackedFoodEntity(
    @PrimaryKey val id: Int?,

    val name: String,
    val imageUrl: String?,

    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val calories: Int,

    val mealType: String,       // ie: breakfast, lunch, dinner, snack, etc.
    val amount: Int,            // amount of food in grams

    val dayOfMonth: Int,        // date of food entry
    val month: Int,             // month of food entry
    val year: Int,              // year of food entry
)
