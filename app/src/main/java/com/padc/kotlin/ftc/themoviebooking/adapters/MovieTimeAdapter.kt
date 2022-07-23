package com.padc.kotlin.ftc.themoviebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieTimeViewHolder

class MovieTimeAdapter : RecyclerView.Adapter<MovieTimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time, parent, false)
        return MovieTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieTimeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 6
}