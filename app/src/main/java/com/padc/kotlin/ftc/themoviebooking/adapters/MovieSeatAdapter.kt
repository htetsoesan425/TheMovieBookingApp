package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.SeatingPlanVO
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.SeatingPlanDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.MovieSeatViewHolder

class MovieSeatAdapter(private val mDelegate: SeatingPlanDelegate) :
    RecyclerView.Adapter<MovieSeatViewHolder>() {

    private var mSeatingPlanVOList: List<SeatingPlanVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSeatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_seat, parent, false)
        return MovieSeatViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieSeatViewHolder, position: Int) {
        if (mSeatingPlanVOList.isNotEmpty()) {
            holder.bindNetworkData(mSeatingPlanVOList[position])
        }
    }

    override fun getItemCount(): Int {
        return mSeatingPlanVOList.count()
    }

/*    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieSeats: List<MovieSeatVO>){
        this.mMovieSeats = movieSeats
        notifyDataSetChanged()
    }*/

    @SuppressLint("NotifyDataSetChanged")
    fun setNetworkData(seatingPlanVOList: List<SeatingPlanVO>) {
        mSeatingPlanVOList = seatingPlanVOList
        notifyDataSetChanged()
    }
}