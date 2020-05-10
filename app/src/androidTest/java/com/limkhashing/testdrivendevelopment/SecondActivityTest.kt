package com.limkhashing.testdrivendevelopment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.limkhashing.testdrivendevelopment.SecondActivity.Companion.buildToastMessage
import com.limkhashing.testdrivendevelopment.matcher.ToastMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SecondActivityTest {

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(SecondActivity::class.java)

    @Test
    fun isActivityInView() {
        onView(withId(R.id.activity_secondary_title)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun visibilityTitleAndNextButton() {
        onView(withId(R.id.activity_secondary_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.button_back))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTitleTextDisplayed() {
        onView(withId(R.id.activity_secondary_title))
            .check(matches(withText("Second Activity")))
    }

    @Test
    fun testShowDialogCaptureNameInput() {
        val NAME = "Kha Shing"

        // Execute and Verify
        onView(withId(R.id.button_open_dialog)).perform(click())
        onView(withText(R.string.text_enter_name)).check(matches(isDisplayed()))
        onView(withText(R.string.text_ok)).perform(click())

        // make sure dialog is still visible (can't click ok without entering a name)
        onView(withText(R.string.text_enter_name)).check(matches(isDisplayed()))

        // enter a name
        onView(withId(R.id.md_input_message)).perform(typeText(NAME))
        onView(withText(R.string.text_ok)).perform(click())

        // make sure dialog is gone
        onView(withText(R.string.text_enter_name)).check(doesNotExist())
        onView(withId(R.id.activity_secondary_title)).check(matches(withText(NAME)))

        // Is toast displayed and is the message correct?
        onView(withText(buildToastMessage(NAME))).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }
}
