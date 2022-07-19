package com.realityexpander.onboarding_domain.di

import com.realityexpander.onboarding_domain.use_cases.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped // must match the InstallIn annotation
    fun provideValidateNutrients(): ValidateNutrients {
        return ValidateNutrients()
    }
}