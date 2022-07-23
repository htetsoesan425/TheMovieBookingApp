package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.DateAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieTimeListAdapter
import kotlinx.android.synthetic.main.activity_movie_time.*

class MovieTimeActivity : AppCompatActivity() {

    private lateinit var mMovieTimeListAdapter: MovieTimeListAdapter
    private lateinit var mDateAdapter: DateAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieTimeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_time)

        setUpRecyclerView()
        setUpListener()
    }

    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnNext.setOnClickListener {
            startActivity(SeatingPlanActivity.newIntent(this))
        }
    }

    private fun setUpRecyclerView() {
        mDateAdapter = DateAdapter()
        rvDate.adapter = mDateAdapter
        rvDate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mMovieTimeListAdapter = MovieTimeListAdapter()
        rvPlaceTime.adapter = mMovieTimeListAdapter
        rvPlaceTime.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}