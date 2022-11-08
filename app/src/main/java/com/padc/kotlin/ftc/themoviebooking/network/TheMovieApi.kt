package com.padc.kotlin.ftc.themoviebooking.network

import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.network.responses.GetActorsResponse
import com.padc.kotlin.ftc.themoviebooking.network.responses.MovieListResponse
import com.padc.kotlin.ftc.themoviebooking.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieApi {
    @GET(API_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Call<MovieListResponse>

    @GET(API_GET_COMING_SOON)
    fun getComingSoonMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Call<MovieListResponse>

    @GET("$API_GET_MOVIE_DETAIL/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Call<MovieVO>


    @GET(API_GET_ACTOR)
    fun getActors(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Call<GetActorsResponse>

}