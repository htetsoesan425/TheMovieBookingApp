package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapConfirmDelegate

class ViewPagerEntryTypeListViewHolder(
    itemView: View,
    private val mDelegate: OnTapConfirmDelegate
) :
    RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            mDelegate.onTapConfirm()
        }
    }
}