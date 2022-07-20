package com.realityexpander.calorietrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.realityexpander.calorietrack.navigation.navigate
import com.realityexpander.calorietrack.ui.theme.CalorieTrackTheme
import com.realityexpander.core.navigation.Route
import com.realityexpander.onboarding_presentation.welcome_screen.WelcomeScreen
import com.realityexpander.onboarding_presentation.activity_level_screen.ActivityLevelScreen
import com.realityexpander.onboarding_presentation.age_screen.AgeScreen
import com.realityexpander.onboarding_presentation.gender_screen.GenderScreen
import com.realityexpander.onboarding_presentation.goal_type_screen.GoalTypeScreen
import com.realityexpander.onboarding_presentation.height_screen.HeightScreen
import com.realityexpander.onboarding_presentation.nutrient_goal_screen.NutrientGoalScreen
import com.realityexpander.onboarding_presentation.weight_screen.WeightScreen
import com.realityexpander.tracker_presentation.search.SearchScreen
import com.realityexpander.tracker_presentation.tracker_overview_screen.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // due to injecting viewModels in the composables
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)  // For the LocalSoftwareKeyboardController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY_LEVEL) {
                            ActivityLevelScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GOAL_TYPE) {
                            GoalTypeScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                //scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealOfDayName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealOfDayName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {
                            val mealOfDayName = it.arguments?.getString("mealOfDayName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealOfDayName = mealOfDayName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
                
            }
        }
    }
}