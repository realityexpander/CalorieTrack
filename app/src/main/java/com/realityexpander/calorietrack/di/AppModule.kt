package com.realityexpander.calorietrack.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.realityexpander.core.data.preferences.DefaultPreferences
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.domain.use_case.FilterKeepDigitsAndDecimals
import com.realityexpander.core.domain.use_case.FilterKeepDigits
import com.realityexpander.onboarding_domain.use_cases.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideFilterOutDigitsUseCase(): FilterKeepDigits {
        return FilterKeepDigits()
    }

    @Provides
    @Singleton
    fun provideFilterOutDecimalsUseCase(): FilterKeepDigitsAndDecimals {
        return FilterKeepDigitsAndDecimals()
    }

}