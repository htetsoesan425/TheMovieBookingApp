package com.padc.kotlin.ftc.themoviebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieTimeListViewHolder

class MovieTimeListAdapter : RecyclerView.Adapter<MovieTimeListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time_list, parent, false)
        return MovieTimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieTimeListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 4
}