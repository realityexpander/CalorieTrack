package com.plcoding.tracker_data.remote.dto


import com.squareup.moshi.Json

data class NutrimentsDto(
    @field:Json(name = "carbohydrates_100g")
    val carbohydrates100g: Double,
    @field:Json(name = "fat_100g")
    val fat100g: Double,
    @field:Json(name = "proteins_100g")
    val proteins100g: Double,
    @field:Json(name = "energy-kcal_100g")
    val energyKcal100g: Double,

    @field:Json(name = "carbohydrates")
    val carbohydratesg: Double,
    @field:Json(name = "fat")
    val fatsg: Double,
    @field:Json(name = "proteins")
    val proteinsg: Double,
    @field:Json(name = "energy-kcal")
    val energyKcalg: Double,
)