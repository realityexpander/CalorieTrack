package com.realityexpander.calorietrack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.common.truth.Truth.assertThat
import com.realityexpander.calorietrack.navigation.Route
import com.realityexpander.calorietrack.repository.TrackerRepositoryFake
import com.realityexpander.calorietrack.ui.theme.CalorieTrackTheme
import com.realityexpander.core.R
import com.realityexpander.core.domain.model.ActivityLevel
import com.realityexpander.core.domain.model.Gender
import com.realityexpander.core.domain.model.GoalType
import com.realityexpander.core.domain.model.UserInfo
import com.realityexpander.core.domain.preferences.Preferences
import com.realityexpander.core.domain.use_case.FilterKeepDigits
import com.realityexpander.tracker_domain.model.TrackableFood
import com.realityexpander.tracker_domain.use_cases.*
import com.realityexpander.tracker_presentation.search.SearchScreen
import com.realityexpander.tracker_presentation.search.SearchViewModel
import com.realityexpander.tracker_presentation.tracker_overview_screen.TrackerOverviewScreen
import com.realityexpander.tracker_presentation.tracker_overview_screen.TrackerOverviewViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    // allows use of Hilt injection
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    // Allows for compose testing
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repositoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        // hiltRule.inject() // to inject dependencies with @Inject annotated fields

        preferences = mockk(relaxed = true)

        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 160f,
            height = 65f,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.MaintainWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.4f,
            fatRatio = 0.3f
        )

        repositoryFake = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            addTrackedFood = AddTrackedFood(repositoryFake),
            searchFood = SearchFood(repositoryFake),
            getFoodsForDate = GetFoodsForDate(repositoryFake),
            deleteTrackedFood = DeleteTrackedFood(repositoryFake),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
        trackerOverviewViewModel = TrackerOverviewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases,
        )
        searchViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterKeepDigits = FilterKeepDigits()
        )

        composeRule.setContent {
            CalorieTrackTheme {  // must wrap in theme to prevent "Failed to inject touch input" error

                navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealOfDayName, dayOfMonth, month, year ->
                                    navController.navigate(
                                        route = Route.SEARCH +
                                                "/${mealOfDayName}" +
                                                "/${dayOfMonth}" +
                                                "/${month}" +
                                                "/${year}"
                                    )
                                },
                                viewModel = trackerOverviewViewModel
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
                                },
                                viewModel = searchViewModel
                            )
                        }
                    }
                }
            }
        }
    }


    @Test // cant use backticks for instrumented tests
    //  *testing functionality, _asserting UI result, and _asserting that the data is correct
    fun addBreakfast_appearsUnderBreakfast_nutrientsProperlyCalculated() {

        /////////////
        // Arrange //
        /////////////

        repositoryFake.searchResults = listOf(
            TrackableFood(
                name = "Apple",
                imageUrl = null,
                caloriesPer100g = 150,
                proteinPer100g = 5,
                carbPer100g = 50,
                fatPer100g = 1
            )
        )
        val amountToAdd = 150
        val mealOfDayName = "Breakfast"


        /////////
        // Act //
        /////////

        // Make sure the "Add Breakfast" button is not visible
        composeRule
            .onNodeWithText("Add $mealOfDayName")
            .assertDoesNotExist()

        // twirl down to show the meals and Add breakfast button
        composeRule
            .onNodeWithContentDescription(mealOfDayName)
            .performClick()

        // Should now be showing the Add button
        composeRule
            .onNodeWithText("Add $mealOfDayName")
            .assertIsDisplayed()

        // click the Add Breakfast button
        composeRule
            .onNodeWithText("Add $mealOfDayName")
            .performClick()

        // Are we at the SEARCH screen?
        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.SEARCH)
        ).isTrue()

        // Enter search text (does not matter due to returning repositoryFake.searchResults)
        composeRule
            .onNodeWithTag("search_text_field")
            .performTextInput("DOES_NOT_MATTER")

        // Perform the search by clicking the "search" icon
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.search))
            .performClick()

        // Twirl on the first result (contains a "carbs" text)
        composeRule
            .onNodeWithText("Carbs")
            .performClick()

        // Should now be showing the Add amount text input & add button
        composeRule
            .onNodeWithText("Add")
            .assertIsDisplayed()

        // Enter the amount
        composeRule
            .onNodeWithContentDescription(composeRule.activity.getString(R.string.amount))
            .performTextInput(amountToAdd.toString())

        // Click the add button
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.add))
            .performClick()

        // Are we at the TRACKER_OVERVIEW screen?
        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Route.TRACKER_OVERVIEW)
        ).isTrue()


        //////////////
        // Assert   //
        //////////////

        val expectedCalories    = (1.5f * 150).roundToInt()
        val expectedCarb        = (1.5f * 50).roundToInt()
        val expectedProtein     = (1.5f * 5).roundToInt()
        val expectedFat         = (1.5f * 1).roundToInt()

        // Check that the expectedCalories is *somewhere* on our screen
        composeRule
            .onAllNodesWithText(expectedCalories.toStr())
            .onFirst() // Just find the first one
            .assertIsDisplayed()

        // Check that the expectedCarb is *somewhere* on our screen
        composeRule
            .onAllNodesWithText(expectedCarb.toStr())
            .onFirst() // Just find the first one
            .assertIsDisplayed()

        // Check that the expectedProtein is *somewhere* on our screen
        composeRule
            .onAllNodesWithText(expectedProtein.toStr())
            .onFirst() // Just find the first one
            .assertIsDisplayed()

        // Check that the expectedFat is *somewhere* on our screen
        composeRule
            .onAllNodesWithText(expectedFat.toStr())
            .onFirst() // Just find the first one
            .assertIsDisplayed()
    }



}


































