package com.dogbreeds.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.dogbreeds.app.NavHostActivity
import com.dogbreeds.app.R
import com.dogbreeds.app.data.server.MockWebServerRule
import com.dogbreeds.app.data.server.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    private val activity = ActivityTestRule(NavHostActivity::class.java, false, false)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(MockResponse().fromJson("breeds.json"))

        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        activity.launchActivity(null)
    }

    @Test
    fun breeds_screen_is_displayed() {
        onView(withId(R.id.breedsAppBar)).check(matches(isDisplayed()))
    }

    @Test
    fun breeds_list_is_loaded() {
        composeTestRule.onNodeWithText("Affenpinscher").assertIsDisplayed()
        composeTestRule.onNodeWithText("American Terrier").assertIsDisplayed()
    }
}
