package com.limkhashing.testdrivendevelopment.util

import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

// option 1: Long Way and difficult to read and is more verbose
// This option can be use if you want to control over the error handling
class EspressoIdlingResourceRule1 : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return IdlingResourceStatement(base)
    }

    inner class IdlingResourceStatement(private val base: Statement?) : Statement() {
        private val idlingResource = EspressoIdlingResource.countingIdlingResource
        override fun evaluate() {
            // before
            IdlingRegistry.getInstance().register(idlingResource)

            // after
            try {
                base?.evaluate() ?: throw Exception("Error evaluating test. Base statement is null")
            } finally {
                IdlingRegistry.getInstance().unregister(idlingResource)
            }
        }
    }
}

// option 2: Simplified version of option 1 (TestWatcher class implement TestRule)
class EspressoIdlingResourceRule2 : TestWatcher() {
    private val idlingResource = EspressoIdlingResource.countingIdlingResource

    // before
    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    // after
    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }
}
