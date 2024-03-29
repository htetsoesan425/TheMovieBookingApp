package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieViewHolder

class MovieAdapter(private val mDelegate: MovieViewHolderDelegate) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var mMovieList: List<MovieVO> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie_list, parent, false)
        return MovieViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (mMovieList.isNotEmpty()) {
            holder.bindData(mMovieList[position])
        }
    }

    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movieList: List<MovieVO>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }
}