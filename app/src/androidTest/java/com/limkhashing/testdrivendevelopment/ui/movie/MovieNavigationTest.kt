package com.limkhashing.testdrivendevelopment.ui.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.limkhashing.testdrivendevelopment.FragmentActivity
import com.limkhashing.testdrivendevelopment.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieNavigationTest {

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(FragmentActivity::class.java)
    val LIST_ITEM_IN_TEST = 4

    @Test
    fun testMovieFragmentsNavigation() {

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(
                    LIST_ITEM_IN_TEST,
                    click()
                )
            )

        // Nav DirectorsFragment
        onView(withId(R.id.movie_directors)).perform(click())
        onView(withId(R.id.fragment_directors)).check(matches(isDisplayed()))

        // Back to Movie Details Fragment
        pressBack()
        onView(withId(R.id.fragment_movie_detail)).check(matches(isDisplayed()))

        // Nav StarActor Fragment
        onView(withId(R.id.movie_star_actors)).perform(click())
        onView(withId(R.id.fragment_star_actors)).check(matches(isDisplayed()))
    }
}
