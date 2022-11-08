package com.padc.kotlin.ftc.themoviebooking.viewpods

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themoviebooking.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie_list.view.*

class MovieViewHolder(itemView: View, private val mDelegate: MovieViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie -> mDelegate.onTapMovie(movieId = movie.id) }
        }
    }

    fun bindData(movie: MovieVO) {
        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivMovieImage)
        itemView.tvMovieName.text = movie.title
        itemView.tvMovieGenre.text = "Action/Family . 1h 30m"
    }

}