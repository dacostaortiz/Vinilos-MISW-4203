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

@RunWith(AndroidJUnit4::class)
class AlbumFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAlbumFragmentVisibilityAfterNavigation() {
        onView(withId(R.id.albumContainer)).perform(click())

        onView(allOf(withText("Álbums"), isDescendantOfA(withId(R.id.albumFragment))))
            .check(matches(isDisplayed()))

        onView(withId(R.id.albumsRv)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withText("Mira todos los álbums que tenemos para ti")).check(matches(isDisplayed()))
    }

    @Test
    fun testAlbumFragmentRecyclerViewItemClick() {
        onView(withId(R.id.albumContainer)).perform(click())

        onView(withId(R.id.albumsRv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

    }

    @Test
    fun testTextViewVisibilityInAlbumFragment() {
        onView(withId(R.id.albumContainer)).perform(click())

        onView(withText("Mira todos los álbums que tenemos para ti")).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewVisibilityInAlbumFragment() {
        onView(withId(R.id.albumContainer)).perform(click())

        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
    }
}