package com.realityexpander.onboarding_domain.use_cases

import com.realityexpander.core.util.UiText
import com.realityexpander.core.R

class ValidateNutrients {

    operator fun invoke(
        carbsRatioStr: String,
        proteinRatioStr: String,
        fatRatioStr: String,
    ): Result {
        val carbsRatio = carbsRatioStr.toIntOrNull()
        val proteinRatio = proteinRatioStr.toIntOrNull()
        val fatRatio = fatRatioStr.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))
        }

        if (carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))
        }

        return Result.Success(
            carbsRatio.toFloat() / 100.0f,
            proteinRatio.toFloat() / 100.0f,
            fatRatio.toFloat() / 100.0f
        )
    }

    sealed class Result {
        data class Success(
            val carbRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ) : Result()

        data class Error(
            val errorMessage: UiText,
        ) : Result()
    }
}