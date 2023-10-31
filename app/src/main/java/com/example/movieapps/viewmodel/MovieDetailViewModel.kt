package com.example.movieapps.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapps.data.DataSource
import com.example.movieapps.model.Movie
import com.example.movieapps.repository.MovieDBContainer
import kotlinx.coroutines.launch

sealed interface MovieDetailUIState{
    data class Success(val data: Movie): MovieDetailUIState
    object Error: MovieDetailUIState
    object Loading: MovieDetailUIState
}

class MovieDetailViewModel: ViewModel() {
    private lateinit var data: Movie
    var movieDetailUIState: MovieDetailUIState by mutableStateOf(MovieDetailUIState.Loading)
        private set

    fun getMovieById(id:Int){
        viewModelScope.launch {
            data = MovieDBContainer().movieDBRepositories.getMovieDetail(id)
            movieDetailUIState = MovieDetailUIState.Success(data)
        }
    }
}