package com.limkhashing.testdrivendevelopment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    // TDD is u write test first then write code

    // For every test, we need to tell what kind of Activity we want to test
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.limkhashing.testdrivendevelopment", appContext.packageName)
    }

    @Test
    fun enterFirstName() {
        onView(withId(R.id.first_name)).perform(typeText("KhaShing"))
    }

    @Test
    fun enterLastName() {
        onView(withId(R.id.first_name)).perform(typeText("Lim"))
    }

    @Test
    fun checkFirstNameLastNameMessage() {
        onView(withId(R.id.first_name)).perform(typeText("KhaShing"))
        onView(withId(R.id.last_name)).perform(typeText("Lim"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.message)).check(matches(withText("Welcome, Lim KhaShing")))
    }

    // Assert that a view is not displayed
    @Test
    fun checkViewDisplayed() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    // Assert that a view is not displayed
    @Test
    fun checkViewIsNotDisplayed() {
        onView(withId(R.id.hide_button)).perform(click())
        onView(withId(R.id.message)).check(matches(not(isDisplayed())))
    }

    // Assert that a view is not present
    @Test
    fun checkViewPresent() {
        // doesNotExist() checks whether a view exists at all
        onView(withId(R.id.button)).check(matches(not(doesNotExist())))
    }
}
