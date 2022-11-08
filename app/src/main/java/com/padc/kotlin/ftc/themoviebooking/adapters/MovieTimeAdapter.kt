package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapMovieTimeDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieTimeViewHolder

class MovieTimeAdapter(private val movieTimeDelegate: OnTapMovieTimeDelegate) :
    RecyclerView.Adapter<MovieTimeViewHolder>() {

    private var mCinemaId: Int? = 0
    private var mTimeSlots: List<TimeSlotVO> = listOf()
    private var mCinemaName: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time, parent, false)
        return MovieTimeViewHolder(view, movieTimeDelegate)
    }

    override fun onBindViewHolder(holder: MovieTimeViewHolder, position: Int) {
        holder.bindData(mTimeSlots[position], mCinemaName, mCinemaId)
    }

    override fun getItemCount(): Int = mTimeSlots.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeslots: List<TimeSlotVO>, cinemaName: String?, cinemaId: Int?) {
        mTimeSlots = timeslots
        mCinemaId = cinemaId
        mCinemaName = cinemaName
        notifyDataSetChanged()
    }
}