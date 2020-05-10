package com.limkhashing.testdrivendevelopment.data.source

import com.limkhashing.testdrivendevelopment.data.FakeMovieData
import com.limkhashing.testdrivendevelopment.data.Movie

class MoviesRemoteDataSource : MoviesDataSource {

    private var MOVIES_REMOTE_DATA = LinkedHashMap<Int, Movie>(FakeMovieData.movies.size)

    init {
        for (movie in FakeMovieData.movies) {
            addMovie(movie)
        }
    }

    override fun getMovies(): List<Movie> {
        return ArrayList(MOVIES_REMOTE_DATA.values)
    }

    override fun getMovie(movieId: Int): Movie? {
        return MOVIES_REMOTE_DATA[movieId]
    }

    private fun addMovie(movie: Movie) {
        MOVIES_REMOTE_DATA[movie.id] = movie
    }
}
