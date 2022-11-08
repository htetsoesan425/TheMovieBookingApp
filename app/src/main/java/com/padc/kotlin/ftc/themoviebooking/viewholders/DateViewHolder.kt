package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotDateVO
import com.padc.kotlin.ftc.themoviebooking.delegates.TimeSlotDelegate
import kotlinx.android.synthetic.main.view_holder_date.view.*

class DateViewHolder(itemView: View, private val mDelegate: TimeSlotDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mSelectedPosition: Int = 0
    private var mTimeSlotDataVO: TimeSlotDateVO? = null

    init {
        itemView.setOnClickListener {
            mTimeSlotDataVO?.let { TimeSlotVO ->
                mDelegate.onTapDate(TimeSlotVO.date, mSelectedPosition)


            }
        }
    }

    fun bindData(timeSlotDateVO: TimeSlotDateVO, selectedPosition: Int) {
        mSelectedPosition = selectedPosition
        mTimeSlotDataVO = timeSlotDateVO
        if (timeSlotDateVO.isSelected) {
            /*itemView.tvDayOfWeek.textSize =
                itemView.context.resources.getDimension(R.dimen.text_small)
            itemView.tvDate.textSize = itemView.resources.getDimension(R.dimen.text_small)*/

            itemView.tvDayOfWeek.setTextColor(
                ContextCompat.getColor(
                    itemView.context, R.color.white
                )
            )
            itemView.tvDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        } else {
            itemView.tvDayOfWeek.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_gray
                )
            )
            itemView.tvDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_gray
                )
            )
        }

        itemView.tvDayOfWeek.text = timeSlotDateVO.dayName
        itemView.tvDate.text = timeSlotDateVO.day
    }
}