package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotDateVO
import com.padc.kotlin.ftc.themoviebooking.delegates.TimeSlotDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.DateViewHolder

class DateAdapter(private val mDelegate: TimeSlotDelegate, private var selectedPosition: Int) :
    RecyclerView.Adapter<DateViewHolder>() {
    private var mDateList: MutableList<TimeSlotDateVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_date, parent, false)
        return DateViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        if (mDateList.isNotEmpty()) {
            holder.bindData(mDateList[position], selectedPosition)
        }
    }

    override fun getItemCount(): Int = mDateList.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(dateList: MutableList<TimeSlotDateVO>) {
        mDateList = dateList
        notifyDataSetChanged()
    }
}