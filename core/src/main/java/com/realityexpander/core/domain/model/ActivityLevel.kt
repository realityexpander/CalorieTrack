package com.realityexpander.core.domain.model

sealed class ActivityLevel(val name: String) {
    object Low: ActivityLevel("low")
    object Medium: ActivityLevel("medium")
    object High: ActivityLevel("high")

    companion object {
        fun fromString(activityLevel: String): ActivityLevel {
            return when(activityLevel) {
                "low" -> Low
                "medium" -> Medium
                "high" -> High
                else -> throw IllegalStateException("Activity Level is unknown.")

            }
        }

        fun fromGoalType(activityLevel: ActivityLevel): String {
            return activityLevel.name
        }
    }
}
