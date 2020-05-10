package com.limkhashing.testdrivendevelopment

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun isActivityInView() {
        // you can put local activity here, but also you can put as Global and call it in each test function
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun visibilityTitleAndNextButton() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.activity_main_title)).check(matches(isDisplayed()))
        onView(withId(R.id.next)).check(matches(isDisplayed())) // method 1
        onView(withId(R.id.next)).check(matches(withEffectiveVisibility(Visibility.VISIBLE))) // method 2
    }

    @Test
    fun isTitleTextDisplayed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.activity_main_title)).check(matches(withText("Main Activity")))
    }

    @Test
    fun backPressToMainActivityu() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.next)).perform(ViewActions.click())
        onView(withId(R.id.second_activity)).check(matches(isDisplayed()))

        onView(withId(R.id.button_back)).perform(ViewActions.click()) // method 1
//        pressBack() // method 2 for back press
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }
}
