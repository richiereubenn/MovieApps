package com.example.movieapps.repository

import com.example.movieapps.model.Movie
import com.example.movieapps.service.MovieDBServices
import java.text.SimpleDateFormat

class MovieDBRepositories(private val movieDBServices: MovieDBServices) {
    suspend fun getAllMovies(page: Int = 1):List<Movie>{
        val ListRawMovie = movieDBServices.getAllMovie().results
        val data = mutableListOf<Movie>()
        for(rawMovie in ListRawMovie){
            val movie = Movie(
                rawMovie.id,
                rawMovie.overview,
                rawMovie.poster_path,
                SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date),
                rawMovie.title,
                rawMovie.vote_average.toFloat(),
                rawMovie.vote_count
            )
            data.add(movie)
        }
        return data
    }

    suspend fun getMovieDetail(movieId:Int): Movie{
        val rawMovie = movieDBServices.getMovieDetail(movieId)
        val data = Movie(
            rawMovie.id,
            rawMovie.overview,
            rawMovie.poster_path,
            SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date),
            rawMovie.title,
            rawMovie.vote_average.toFloat(),
            rawMovie.vote_count
        )
        return data
    }
}