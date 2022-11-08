package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapMovieTimeDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieTimeListViewHolder

class MovieTimeListAdapter(private val mDelegate: OnTapMovieTimeDelegate) :
    RecyclerView.Adapter<MovieTimeListViewHolder>() {

    private var mCinemaTimeSlotVOList: List<CinemaTimeSlotVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time_list, parent, false)
        return MovieTimeListViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieTimeListViewHolder, position: Int) {
        holder.bindData(
            mCinemaTimeSlotVOList[position],
            mCinemaTimeSlotVOList[position].cinemaId,
            mCinemaTimeSlotVOList[position].cinema
        )
    }

    override fun getItemCount(): Int {
        return mCinemaTimeSlotVOList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaTimeSlotVOList: List<CinemaTimeSlotVO>) {
        mCinemaTimeSlotVOList = cinemaTimeSlotVOList
        notifyDataSetChanged()
    }
}