package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapMovieTimeDelegate
import kotlinx.android.synthetic.main.view_holder_movie_time.view.*

class MovieTimeViewHolder(itemView: View, private val movieTimeDelegate: OnTapMovieTimeDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mCinemaId: Int? = 0
    private var mCinemaName: String? = ""
    private var mTimeSlot: TimeSlotVO? = null

    init {
        itemView.setOnClickListener {
            mTimeSlot?.let { timeSlotVO ->
                movieTimeDelegate.onTapMovieTime(
                    timeSlotVO.cinemaDayTimeslotId,
                    timeSlotVO.startTime,
                    mCinemaName,
                    mCinemaId
                )
            }
        }
    }

    fun bindData(timeSlotVO: TimeSlotVO, cinemaName: String?, cinemaId: Int?) {

        mCinemaName = cinemaName
        mCinemaId = cinemaId
        mTimeSlot = timeSlotVO

        itemView.tvStartTime.text = timeSlotVO.startTime
        if (timeSlotVO.isSelected) {
            itemView.llMovieTime.background =
                itemView.context.getDrawable(R.drawable.background_movie_time_selected)
            itemView.tvStartTime.setTextColor(itemView.context.getColor(R.color.white))
        } else {
            itemView.llMovieTime.background =
                itemView.context.getDrawable(R.drawable.background_movie_time)
            itemView.tvStartTime.setTextColor(itemView.context.getColor(R.color.black))
        }
    }
}