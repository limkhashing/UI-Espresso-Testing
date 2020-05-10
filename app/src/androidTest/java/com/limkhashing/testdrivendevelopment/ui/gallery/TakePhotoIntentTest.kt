package com.limkhashing.testdrivendevelopment.ui.gallery

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.limkhashing.testdrivendevelopment.KEY_IMAGE_DATA
import com.limkhashing.testdrivendevelopment.MainActivity
import com.limkhashing.testdrivendevelopment.R
import com.limkhashing.testdrivendevelopment.matcher.ImageViewHasDrawableMatcher.hasDrawable
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TakePhotoIntentTest {

    @Rule
    @JvmField
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testCameraIntentAndSetToImageView() {
        // since we only have one intent, just use hasAction()
        val expectedIntent = hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        val activityResult = createImageCaptureActivityResultStub()
        intending(expectedIntent).respondWith(activityResult)

        // Execute and Verify
        // Check is drawable set to the imageView
        onView(withId(R.id.image)).check(matches(not(hasDrawable())))
        onView(withId(R.id.button_camera)).perform(click())
        intended(expectedIntent)
        onView(withId(R.id.image)).check(matches(hasDrawable()))
    }

    private fun createImageCaptureActivityResultStub(): ActivityResult? {
        val bundle = Bundle()
        bundle.putParcelable(
            KEY_IMAGE_DATA, BitmapFactory.decodeResource(
                intentsTestRule.activity.resources,
                R.drawable.ic_launcher_background
            )
        )
        val resultData = Intent()
        resultData.putExtras(bundle)
        return ActivityResult(Activity.RESULT_OK, resultData)
    }
}


