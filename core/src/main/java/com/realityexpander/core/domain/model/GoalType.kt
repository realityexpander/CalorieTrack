package com.realityexpander.core.domain.model

sealed class GoalType(val name: String) {
    object LoseWeight: GoalType("lose_weight")
    object MaintainWeight: GoalType("maintain_weight")
    object GainWeight: GoalType("gain_weight")

    companion object {
        fun fromString(goalType: String): GoalType {
            return when(goalType) {
                "lose_weight" -> LoseWeight
                "maintain_weight" -> MaintainWeight
                "gain_weight" -> GainWeight
                else -> MaintainWeight //throw IllegalStateException("Goal type is unknown.")

            }
        }

        fun fromGoalType(goalType: GoalType): String {
            return goalType.name
        }
    }
}
