package com.realityexpander.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Float,
    val height: Float,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float,
)