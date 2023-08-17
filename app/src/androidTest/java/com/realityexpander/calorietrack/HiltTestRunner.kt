package com.realityexpander.calorietrack

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication


// Sets up the application for testing with Hilt.
// reference: https://developer.android.com/training/testing/hilt-android#hilt-testing-application
// Using this line in gradle:
//   testInstrumentationRunner = "com.realityexpander.calorietrack.HiltTestRunner"
class HiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}