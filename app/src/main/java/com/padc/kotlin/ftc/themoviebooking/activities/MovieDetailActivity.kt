package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.CastAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.GenreChipAdapter
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java)
        }
    }

    lateinit var mGenreChipAdapter: GenreChipAdapter
    lateinit var mCastAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setUpRecyclerView()
        setUpListener()
    }


    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
        btnGetYourTicket.setOnClickListener {
            startActivity(MovieTimeActivity.newIntent(this))
        }
    }

    private fun setUpRecyclerView() {
        mGenreChipAdapter = GenreChipAdapter()
        rvChipGenre.adapter = mGenreChipAdapter
        rvChipGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mCastAdapter = CastAdapter()
        rvCast.adapter = mCastAdapter
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
}