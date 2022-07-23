package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieTimeAdapter
import kotlinx.android.synthetic.main.view_holder_movie_time_list.view.*

class MovieTimeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var mMovieTimeAdapter: MovieTimeAdapter

    init {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mMovieTimeAdapter = MovieTimeAdapter()
        itemView.rvMovieTime.adapter = mMovieTimeAdapter
        itemView.rvMovieTime.layoutManager = GridLayoutManager(itemView.context, 3)
    }
}