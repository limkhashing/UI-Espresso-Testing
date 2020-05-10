package com.limkhashing.testdrivendevelopment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.RequestOptions
import com.limkhashing.testdrivendevelopment.data.source.MoviesDataSource
import com.limkhashing.testdrivendevelopment.data.source.MoviesRemoteDataSource
import com.limkhashing.testdrivendevelopment.factory.MovieFragmentFactory
import com.limkhashing.testdrivendevelopment.ui.movie.MovieListFragment

class FragmentActivity : AppCompatActivity() {

    // dependencies (typically would be injected with dagger)
    lateinit var requestOptions: RequestOptions
    lateinit var moviesDataSource: MoviesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        supportFragmentManager.fragmentFactory = MovieFragmentFactory(
            requestOptions,
            moviesDataSource
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        init()
    }

    private fun init() {
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment::class.java, null)
                .commit()
        }
//        if(supportFragmentManager.fragments.size == 0){
//            val movieId = 1
//            val bundle = Bundle()
//            bundle.putInt("movie_id", movieId)
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MovieDetailFragment::class.java, bundle)
//                .commit()
//        }
    }

    private fun initDependencies() {
        if (!::requestOptions.isInitialized) {
            // glide
            requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.default_image)
        }

        if (!::moviesDataSource.isInitialized) {
            // Data Source
            moviesDataSource = MoviesRemoteDataSource()
        }
    }
}
