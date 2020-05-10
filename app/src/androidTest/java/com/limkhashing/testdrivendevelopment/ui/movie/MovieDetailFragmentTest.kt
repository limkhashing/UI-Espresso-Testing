package com.limkhashing.testdrivendevelopment.ui.movie

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bumptech.glide.request.RequestOptions
import com.limkhashing.testdrivendevelopment.FragmentActivity
import com.limkhashing.testdrivendevelopment.R
import com.limkhashing.testdrivendevelopment.data.FakeMovieData
import com.limkhashing.testdrivendevelopment.data.source.MoviesDataSource
import com.limkhashing.testdrivendevelopment.data.source.MoviesRemoteDataSource
import com.limkhashing.testdrivendevelopment.factory.MovieFragmentFactory
import com.limkhashing.testdrivendevelopment.ui.movie.detail.MovieDetailFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieDetailFragmentTest {

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(FragmentActivity::class.java)

    @Test
    fun isMovieDataVisible() {

        val movie = FakeMovieData.movies[1]

        // NOTE:
        // Also could have built a "FakeMoviesRemoteDataSource" (AKA a STUB).
        // I don't think it matters in this case.
        // Probably for a larger repository and more complex app I would stub the repository. Then
        // you could test errors, various success cases, etc...
//        val moviesDataSource = mockk<MoviesRemoteDataSource>()
//        every {
//            moviesDataSource.getMovie(movie.id)
//        } returns movie

        val moviesDataSource: MoviesDataSource = MoviesRemoteDataSource()
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)

        val fragmentFactory = MovieFragmentFactory(requestOptions, moviesDataSource)
        val bundle = Bundle()
        bundle.putInt("movie_id", movie.id)

        val scenario = launchFragmentInContainer<MovieDetailFragment>(
            fragmentArgs = bundle,
            factory = fragmentFactory
        )

        onView(withId(R.id.movie_title))
            .check(matches(withText(movie.title)))
        onView(withId(R.id.movie_description))
            .check(matches(withText(movie.description)))
    }
}