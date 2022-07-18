package com.realityexpander.core.data.preferences

import android.content.SharedPreferences
import com.realityexpander.core.domain.model.ActivityLevel
import com.realityexpander.core.domain.model.Gender
import com.realityexpander.core.domain.model.GoalType
import com.realityexpander.core.domain.model.UserInfo
import com.realityexpander.core.domain.preferences.Preferences

class DefaultPreferences(
    private val preferences: SharedPreferences
): Preferences {
    override fun saveGender(gender: Gender) {
        preferences.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        preferences.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        preferences.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Float) {
        preferences.edit()
            .putFloat(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        preferences.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, level.name)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        preferences.edit()
            .putString(Preferences.KEY_GOAL_TYPE, type.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        preferences.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        preferences.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        preferences.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        return preferences.all.let { userMap ->
            UserInfo(
                gender          = Gender.fromString(userMap[Preferences.KEY_GENDER] as String),
                age             = userMap[Preferences.KEY_AGE] as Int,
                weight          = userMap[Preferences.KEY_WEIGHT] as Float,
                height          = userMap[Preferences.KEY_HEIGHT] as Float,
                activityLevel   = ActivityLevel.fromString(userMap[Preferences.KEY_GENDER] as String),
                goalType        = GoalType.fromString(userMap[Preferences.KEY_GOAL_TYPE] as String),
                carbRatio       = userMap[Preferences.KEY_CARB_RATIO] as Float,
                proteinRatio    = userMap[Preferences.KEY_PROTEIN_RATIO] as Float,
                fatRatio        = userMap[Preferences.KEY_FAT_RATIO] as Float,
            )
        }
    }
}