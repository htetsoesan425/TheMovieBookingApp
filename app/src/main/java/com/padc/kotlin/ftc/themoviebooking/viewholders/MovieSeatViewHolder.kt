package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.SeatingPlanVO
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.SeatingPlanDelegate
import kotlinx.android.synthetic.main.view_holder_movie_seat.view.*

class MovieSeatViewHolder(itemView: View, private val mDelegate: SeatingPlanDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mSeatingPlanVO: SeatingPlanVO? = null

    init {
        itemView.setOnClickListener {
            mSeatingPlanVO?.let {
                mDelegate.onTapSeat(it.id, it.symbol)
            }
        }
    }

    fun bindNetworkData(data: SeatingPlanVO) {
        mSeatingPlanVO = data
        when {
            data.isMovieSeatAvailable() -> {
                if (data.isSelected) {
                    itemView.tvMovieSeatTitle.visibility = View.VISIBLE
                    itemView.tvMovieSeatTitle.text = data.seatName
                    itemView.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_movie_seat_selected

                    )
                    itemView.tvMovieSeatTitle.setTextColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.white
                        )
                    )
                } else {
                    itemView.tvMovieSeatTitle.visibility = View.GONE
                    itemView.tvMovieSeatTitle.text = data.seatName
                    itemView.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_movie_seat_avaiable
                    )
                }
            }
            data.isMovieSeatTAKEN() -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.tvMovieSeatTitle.text = data.seatName
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_movie_seat_taken
                )
            }

            data.isMovieSeatRowTitle() -> {
                itemView.tvMovieSeatTitle.visibility = View.VISIBLE
                itemView.tvMovieSeatTitle.text = data.symbol
                itemView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )
            }
            else -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )
            }
        }
    }
}