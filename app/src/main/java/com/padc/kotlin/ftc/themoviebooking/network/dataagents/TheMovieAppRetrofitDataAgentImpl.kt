package com.padc.kotlin.ftc.themoviebooking.network.dataagents

import android.util.Log
import com.padc.kotlin.ftc.themoviebooking.data.vos.ActorVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.network.TheMovieApi
import com.padc.kotlin.ftc.themoviebooking.network.responses.GetActorsResponse
import com.padc.kotlin.ftc.themoviebooking.network.responses.MovieListResponse
import com.padc.kotlin.ftc.themoviebooking.utils.BASE_THE_MOVIE_APP_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object TheMovieAppRetrofitDataAgentImpl : MovieDataAgent {

    private var mTheMovieApi: TheMovieApi? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor = interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_THE_MOVIE_APP_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieApi = retrofit.create(TheMovieApi::class.java)
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getNowPlayingMovies()
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        val movieList = response.body()?.results ?: listOf()
                        onSuccess(movieList)
                    } else {
                        Log.d("TAG", "onResponse: " + response.message())
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getComingSoonMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMovieDetail(movieId = movieId)
            ?.enqueue(object : Callback<MovieVO> {
                override fun onResponse(call: Call<MovieVO>, response: Response<MovieVO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it)
                        }
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieVO>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieApi?.getActors()?.enqueue(object : Callback<GetActorsResponse> {
            override fun onResponse(
                call: Call<GetActorsResponse>,
                response: Response<GetActorsResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.result ?: listOf())
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<GetActorsResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

}