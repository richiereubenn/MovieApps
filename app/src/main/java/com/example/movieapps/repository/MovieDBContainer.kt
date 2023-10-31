package com.example.movieapps.repository

import com.example.movieapps.service.MovieDBServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class AuthInterceptor(private val bearerToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(request)
    }
}

class MovieDBContainer {
    companion object{
        val BASE_IMG = "https://image.tmdb.org/t/p/w500"
    }
    private val BASE_URL = "https://api.themoviedb.org/3/movie/"
    private val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYzNhYzU0YWQ4NDMxNDk5YmU4MDc5ODI4MzliY2JhNCIsInN1YiI6IjY1Mzc0YmFkNDFhYWM0MDBjMzMxZWYxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.a0gVwPJOuF7HY7z6lr6rHnyL9PEn-cg5fU5hN6XbIsg"
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
    private val retrofitService: MovieDBServices by lazy{
        retrofit.create(MovieDBServices::class.java)
    }
    val movieDBRepositories:MovieDBRepositories by lazy {
        MovieDBRepositories(retrofitService)
    }
}