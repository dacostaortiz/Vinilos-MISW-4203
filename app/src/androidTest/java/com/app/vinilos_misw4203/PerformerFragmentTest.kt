package com.app.vinilos_misw4203

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.vinilos_misw4203.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.app.vinilos_misw4203.R
import org.hamcrest.Matchers.allOf
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

@RunWith(AndroidJUnit4::class)
class PerformerFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testPerformerFragmentVisibilityAfterNavigation() {
        onView(withId(R.id.performerContainer)).perform(click())

        onView(allOf(withText("Artistas"), isDescendantOfA(withId(R.id.performerFragment))))
            .check(matches(isDisplayed()))

        // Wait for loading to complete
        onView(isRoot()).perform(waitFor(3000))

        onView(withId(R.id.performers_rv)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withText("Descubre todos los artistas disponibles")).check(matches(isDisplayed()))
    }

    @Test
    fun testPerformerFragmentRecyclerViewItemClick() {
        onView(withId(R.id.performerContainer)).perform(click())

        // Wait for data to load
        onView(isRoot()).perform(waitFor(2000))

        onView(withId(R.id.performers_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun testTextViewVisibilityInPerformerFragment() {
        onView(withId(R.id.performerContainer)).perform(click())

        onView(withText("Descubre todos los artistas disponibles")).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewVisibilityInPerformerFragment() {
        onView(withId(R.id.performerContainer)).perform(click())

        onView(withId(R.id.performers_rv)).check(matches(isDisplayed()))
    }

    // Helper method to add delay
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