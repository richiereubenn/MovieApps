package com.example.movieapps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapps.ui.view.ListMovieView
import com.example.movieapps.ui.view.MovieDetailView
import com.example.movieapps.ui.view.ProfileView
import com.example.movieapps.viewmodel.ListMovieUIState
import com.example.movieapps.viewmodel.ListMovieViewModel
import com.example.movieapps.viewmodel.MovieDetailUIState
import com.example.movieapps.viewmodel.MovieDetailViewModel

enum class ListScreen(){
    ListMovie,
    MovieDetail,
    Profile
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppsRoute(){
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold (

    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListScreen.ListMovie.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(ListScreen.ListMovie.name){
                val listMovieViewModel: ListMovieViewModel = viewModel()
                val status = listMovieViewModel.listMovieUIState
                when(status){
                    is ListMovieUIState.Loading -> {}
                    is ListMovieUIState.Success -> ListMovieView(
                        movieList = status.data,
                        onFavClicked = {movie ->
                            listMovieViewModel.onFavClicked(movie)
                        },
                        onCardClick = {
                            navController.navigate(ListScreen.MovieDetail.name+"/"+it.id)
                        }
                    )
                    is ListMovieUIState.Error -> {}
                }
            }
            composable(ListScreen.MovieDetail.name+"/{movieId}"){
                val movieDetailViewModel: MovieDetailViewModel = viewModel()
                movieDetailViewModel.getMovieById(
                    it.arguments?.getString("movieId")!!.toInt()
                )

                val status = movieDetailViewModel.movieDetailUIState
                when(status){
                    is MovieDetailUIState.Loading -> {}
                    is MovieDetailUIState.Success -> {
                        MovieDetailView(
                            movie = status.data,
                            onFavClicked = {}
                        )
                    }
                    is MovieDetailUIState.Error -> {}
                }
            }
            composable(ListScreen.Profile.name){
                ProfileView()
            }
        }
    }
}
