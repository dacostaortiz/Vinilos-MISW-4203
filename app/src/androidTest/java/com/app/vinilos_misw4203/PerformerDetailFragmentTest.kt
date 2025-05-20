package com.app.vinilos_misw4203

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.not

@RunWith(AndroidJUnit4::class)
class PerformerDetailFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPerformerDetailFragmentNavigation() {
        // Navigate to performer list
        onView(withId(R.id.performerContainer)).perform(click())

        // Wait for performers to load
        onView(isRoot()).perform(waitFor(2000))

        // Click on first performer
        onView(withId(R.id.performers_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Wait for performer detail to load
        onView(isRoot()).perform(waitFor(1000))

        // Verify performer detail elements are displayed
        onView(withId(R.id.performerDetailFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.performerImage)).check(matches(isDisplayed()))
        onView(withId(R.id.performerName)).check(matches(isDisplayed()))
        onView(withId(R.id.performerType)).check(matches(isDisplayed()))
        onView(withId(R.id.performerDescription)).check(matches(isDisplayed()))
    }

    @Test
    fun testPerformerDetailFragmentContent() {
        // Navigate to performer list
        onView(withId(R.id.performerContainer)).perform(click())

        // Wait for performers to load
        onView(isRoot()).perform(waitFor(2000))

        // Click on first performer
        onView(withId(R.id.performers_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Wait for performer detail to load
        onView(isRoot()).perform(waitFor(1000))

        // Verify performer name is not empty
        onView(withId(R.id.performerName)).check(matches(withText(not(""))))

        // Verify performer type is not empty
        onView(withId(R.id.performerType)).check(matches(withText(not(""))))

        // Verify performer description is not empty
        onView(withId(R.id.performerDescription)).check(matches(withText(not(""))))
    }

    // Helper method to add delay for async operations
    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}