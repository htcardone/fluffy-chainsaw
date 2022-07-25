package com.podium.technicalchallenge

import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.podium.technicalchallenge.movies.MoviesViewModel
import com.podium.technicalchallenge.utils.MockDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RegressionTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var activity: MainActivity
    private lateinit var viewModel: MoviesViewModel
    private val mockWebServer = MockWebServer()

    init {
        mockWebServer.start(8080)
        mockWebServer.dispatcher = MockDispatcher()
    }

    @Before
    fun setup() {
        composeTestRule.activityRule.scenario.onActivity {
            activity = it
            viewModel = composeTestRule.activity.viewModels<MoviesViewModel>().value
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun homeScreen_showContent() {
        composeTestRule.apply {
            // Home screen
            onNodeWithText("Movie 1").assertIsDisplayed()
            onNodeWithText("Movie 2").assertIsDisplayed()
            onNodeWithText("Movie 3").assertIsDisplayed()
            onNodeWithTag("genres_list").assertIsDisplayed()
            onNodeWithText("Action").assertIsDisplayed()
            onNodeWithText("Comedy").assertIsDisplayed()
            onNodeWithText("View All Movies", ignoreCase = true).assertIsDisplayed()

            // Scroll top movies horizontally to check if all 5 movies are displayed
            onNodeWithTag("top_movies_row").performScrollToIndex(4)
            onNodeWithText("Movie 4").assertIsDisplayed()
            onNodeWithText("Movie 5").assertIsDisplayed()
        }
    }

    @Test
    fun homeScreen_navigateToMovieScreen() {
        composeTestRule.apply {
            // Home screen
            onNodeWithTag("top_movies_row").onChildAt(0).performClick()

            // Movie details screen
            onNodeWithText("Movie Title On Details Screen").performScrollTo()
            onNodeWithText("Movie description on details screen").assertExists()
            onNodeWithTag("genres_row").performScrollTo()
            onNodeWithTag("cast_row").performScrollTo()
            onNodeWithTag("director_item").performScrollTo()
        }
    }

    @Test
    fun homeScreen_navigateToMoviesScreen() {
        composeTestRule.apply {
            // Home screen
            onNodeWithText("Action").performClick()

            // Movies screen
            onNodeWithText("Mortal Kombat Legends: Scorpion's Revenge").assertIsDisplayed()
        }
    }

    @Test
    fun moviesScreen_showContent() {
        composeTestRule.apply {
            // Home screen
            onNodeWithText("Action").performClick()

            // Movies screen
            onNodeWithTag("movies_grid").assertIsDisplayed()
            onNodeWithTag("movies_grid").onChildAt(0).assertHasClickAction()

            onNodeWithText("Action movies").performClick()
            onNodeWithTag("genres_list").assertIsDisplayed()
            onRoot().performClick()

            onNodeWithText("Rating").performClick()
            onAllNodesWithText("Title").assertAll(hasClickAction())
            onAllNodesWithText("Rating").assertAll(hasClickAction())
            onAllNodesWithText("Popularity").assertAll(hasClickAction())
        }
    }

    @Test
    fun moviesScreen_navigateToMovieScreen() {
        composeTestRule.apply {
            // Home screen
            onNodeWithText("Action").performClick()

            // Movies screen
            onNodeWithTag("movies_grid").assertIsDisplayed()
            onNodeWithTag("movies_grid").onChildAt(0).performClick()

            // Movie screen
            onNodeWithText("Movie Title On Details Screen").performScrollTo()
        }
    }
}