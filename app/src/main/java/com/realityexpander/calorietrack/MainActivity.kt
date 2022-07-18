package com.realityexpander.calorietrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.realityexpander.calorietrack.ui.theme.CalorieTrackTheme
import com.realityexpander.onboarding_presentation.welcome_screen.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackTheme {
                WelcomeScreen()
            }
        }
    }
}