package io.buildwithnd.demotmdb.network.services

import io.buildwithnd.demotmdb.model.TrendingMovieResponse
import io.buildwithnd.demotmdb.model.MovieDesc
import io.buildwithnd.demotmdb.model.MovieSearchDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API Service
 */
interface MovieService {
    @GET("/3/trending/movie/week")
    suspend fun getPopularMovies(): Response<TrendingMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): Response<MovieDesc>

    @GET("/3/search/movie")
    suspend fun getMovieList(@Query("query") query: String): Response<MovieSearchDTO>
}