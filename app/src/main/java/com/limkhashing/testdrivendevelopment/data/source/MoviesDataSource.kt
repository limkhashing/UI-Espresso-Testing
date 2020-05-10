package com.limkhashing.testdrivendevelopment.data.source

import com.limkhashing.testdrivendevelopment.data.Movie


interface MoviesDataSource {
    fun getMovie(movieId: Int): Movie?

    fun getMovies(): List<Movie>
}