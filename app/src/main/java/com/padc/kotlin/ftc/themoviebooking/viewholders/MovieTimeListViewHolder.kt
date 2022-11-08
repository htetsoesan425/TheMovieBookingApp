package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieTimeAdapter
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapMovieTimeDelegate
import kotlinx.android.synthetic.main.view_holder_movie_time_list.view.*

class MovieTimeListViewHolder(itemView: View, private val mDelegate: OnTapMovieTimeDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mCinemaId: Int? = 0
    private var mCinemaTimeSlotVO: CinemaTimeSlotVO? = null
    private lateinit var mMovieTimeAdapter: MovieTimeAdapter

    init {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mMovieTimeAdapter = MovieTimeAdapter(mDelegate)
        itemView.rvMovieTime.adapter = mMovieTimeAdapter
        itemView.rvMovieTime.layoutManager = GridLayoutManager(itemView.context, 3)
    }

    fun bindData(cinemaTimeSlotVO: CinemaTimeSlotVO, cinemaId: Int?, cinemaName: String?) {
        mCinemaTimeSlotVO = cinemaTimeSlotVO
        mCinemaId = cinemaId
        itemView.tvCinemaName.text = cinemaTimeSlotVO.cinema
        cinemaTimeSlotVO.timeslots?.let {
            mMovieTimeAdapter.setNewData(it, cinemaName, cinemaId)
        }
    }
}