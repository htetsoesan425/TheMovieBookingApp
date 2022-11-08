package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.CastAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.GenreChipAdapter
import com.padc.kotlin.ftc.themoviebooking.data.vos.ActorVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.GenreVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import com.padc.kotlin.ftc.themoviebooking.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private var mMovieName: String = ""
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {
        private const val IE_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(IE_MOVIE_ID, movieId)
            return intent
        }
    }

    lateinit var mGenreChipAdapter: GenreChipAdapter
    lateinit var mCastAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setUpRecyclerView()
        val movieId = intent?.getIntExtra(IE_MOVIE_ID, 0)

        setUpListener(movieId)

        movieId?.let {
            requestData(it)
        }
    }

    private fun requestData(movieId: Int) {
        mMovieBookingModel.getMovieDetail(
            movieId = movieId.toString(),
            onSuccess = {
                bindData(it)
            },
            onFailure = {
                showError(it)
            })

        mMovieBookingModel.getActors(
            onSuccess = {
                bindCasts(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun bindData(movie: MovieVO) {
        mMovieName = movie.title.toString() //pass to time slot
        colToolBar.title = movie.title
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMoviePoster)
        tvMovieName.text = movie.title
        tvPlotSummary.text = movie.overview
        ratingBarMovie.rating = movie.getRatingBasedOnFiveStar()
        bindGenres(movie = movie, genres = movie.genres ?: listOf())
    }

    private fun bindCasts(actors: List<ActorVO>) {
        mCastAdapter.setNewData(actors)
    }

    private fun bindGenres(movie: MovieVO, genres: List<GenreVO>) {
        mGenreChipAdapter.setNewData(genres)
    }

    private fun setUpRecyclerView() {
        mGenreChipAdapter = GenreChipAdapter()
        rvChipGenre.adapter = mGenreChipAdapter
        rvChipGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mCastAdapter = CastAdapter()
        rvCast.adapter = mCastAdapter
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpListener(movieId: Int?) {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
        btnGetYourTicket.setOnClickListener {
            startActivity(MovieTimeActivity.newIntent(this, movieId, mMovieName))
        }
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }
}