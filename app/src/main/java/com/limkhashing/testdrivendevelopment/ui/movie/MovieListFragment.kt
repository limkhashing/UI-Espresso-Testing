package com.limkhashing.testdrivendevelopment.ui.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.limkhashing.testdrivendevelopment.R
import com.limkhashing.testdrivendevelopment.data.FakeMovieData.FAKE_NETWORK_DELAY
import com.limkhashing.testdrivendevelopment.data.Movie
import com.limkhashing.testdrivendevelopment.data.source.MoviesDataSource
import com.limkhashing.testdrivendevelopment.ui.movie.detail.MovieDetailFragment
import com.limkhashing.testdrivendevelopment.util.EspressoIdlingResource
import com.limkhashing.testdrivendevelopment.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val TAG = MovieListFragment::class.java.simpleName

class MovieListFragment(
    val moviesDataSource: MoviesDataSource
) : Fragment(), MoviesListAdapter.Interaction {

    lateinit var listAdapter: MoviesListAdapter
    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onItemSelected(position: Int, item: Movie) {
        activity?.run {
            val bundle = Bundle()
            bundle.putInt("movie_id", item.id)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieDetailFragment::class.java, bundle)
                .addToBackStack("MovieDetailFragment")
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getData()
    }

    private fun getData() {
        EspressoIdlingResource.increment()
        uiCommunicationListener.loading(true)
        val job = GlobalScope.launch(IO) {
            delay(FAKE_NETWORK_DELAY)
        }
        job.invokeOnCompletion {
            GlobalScope.launch(Main) {
                uiCommunicationListener.loading(false)
                listAdapter.submitList(moviesDataSource.getMovies())
                EspressoIdlingResource.decrement()
            }
        }

        // will delay, like real-world scenario
        // taking data from cache or network API
        listAdapter.submitList(moviesDataSource.getMovies())
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            removeItemDecoration(
                TopSpacingItemDecoration(
                    30
                )
            )
            addItemDecoration(
                TopSpacingItemDecoration(
                    30
                )
            )
            listAdapter = MoviesListAdapter(this@MovieListFragment)
            adapter = listAdapter
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "Must implement interface in $activity: ${e.message}")
        }
    }
}
