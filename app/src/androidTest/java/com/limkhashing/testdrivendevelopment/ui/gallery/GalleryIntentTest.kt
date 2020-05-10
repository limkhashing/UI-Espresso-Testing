package com.limkhashing.testdrivendevelopment.ui.gallery

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.limkhashing.testdrivendevelopment.R
import com.limkhashing.testdrivendevelopment.SecondActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class GalleryIntentTest {

    // https://developer.android.com/training/testing/espresso/intents

    @Rule
    @JvmField
    val intentsTestRule = IntentsTestRule(SecondActivity::class.java)

    @Test
    fun intentSendToPickPackage() {
        // GIVEN
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_PICK),
            hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        )
        val activityResult = createGalleryPickActivityResultStub()
        intending(expectedIntent).respondWith(activityResult)

        // EXECUTE AND VERIFY
        onView(withId(R.id.button_open_gallery)).perform(click())
        intended(expectedIntent)
    }

    private fun createGalleryPickActivityResultStub(): ActivityResult {
        // get context in UI Test
        val resources = InstrumentationRegistry.getInstrumentation().context.resources

        // mock Uri of a local drawable image and make it as dummy intent
        val imageUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    resources.getResourcePackageName(R.drawable.ic_launcher_background) + '/' +
                    resources.getResourceTypeName(R.drawable.ic_launcher_background) + '/' +
                    resources.getResourceEntryName(R.drawable.ic_launcher_background)
        )
        val resultIntent = Intent()
        resultIntent.data = imageUri
        return ActivityResult(RESULT_OK, resultIntent)
    }
}