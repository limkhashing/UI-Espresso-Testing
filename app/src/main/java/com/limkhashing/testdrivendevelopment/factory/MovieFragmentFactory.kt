package com.limkhashing.testdrivendevelopment.factory

import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.request.RequestOptions
import com.limkhashing.testdrivendevelopment.data.source.MoviesDataSource
import com.limkhashing.testdrivendevelopment.ui.movie.MovieListFragment
import com.limkhashing.testdrivendevelopment.ui.movie.actors.StarActorsFragment
import com.limkhashing.testdrivendevelopment.ui.movie.detail.MovieDetailFragment
import com.limkhashing.testdrivendevelopment.ui.movie.directors.DirectorsFragment

class MovieFragmentFactory(
    private val requestOptions: RequestOptions? = null,
    private val moviesDataSource: MoviesDataSource? = null
) : FragmentFactory() {

    private val TAG: String = "AppDebug"

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            MovieListFragment::class.java.name -> {
                if (moviesDataSource != null) {
                    MovieListFragment(moviesDataSource)
                } else {
                    super.instantiate(classLoader, className)
                }
            }

            MovieDetailFragment::class.java.name -> {
                if (requestOptions != null
                    && moviesDataSource != null
                ) {
                    MovieDetailFragment(
                        requestOptions,
                        moviesDataSource
                    )
                } else {
                    super.instantiate(classLoader, className)
                }
            }

            DirectorsFragment::class.java.name -> {
                DirectorsFragment()
            }

            StarActorsFragment::class.java.name -> {
                StarActorsFragment()
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}
