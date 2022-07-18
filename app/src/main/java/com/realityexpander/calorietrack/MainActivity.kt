package com.realityexpander.calorietrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.realityexpander.calorietrack.navigation.navigate
import com.realityexpander.calorietrack.ui.theme.CalorieTrackTheme
import com.realityexpander.core.navigation.Route
import com.realityexpander.onboarding_presentation.welcome_screen.WelcomeScreen
import com.realityexpander.onboarding_presentation.welcome_screen.gender_screen.GenderScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // due to injecting viewmodels in the composables
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.WELCOME
                ) {
                    composable(Route.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.AGE) {
                    }
                    composable(Route.GENDER) {
                        GenderScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.HEIGHT) {
                    }
                    composable(Route.WEIGHT) {
                    }
                    composable(Route.NUTRIENT_GOAL) {
                    }
                    composable(Route.ACTIVITY) {
                    }
                    composable(Route.GOAL) {
                    }
                    composable(Route.TRACKER_OVERVIEW) {
                    }
                    composable(Route.SEARCH) {
                    }
                }

            }
        }
    }
}