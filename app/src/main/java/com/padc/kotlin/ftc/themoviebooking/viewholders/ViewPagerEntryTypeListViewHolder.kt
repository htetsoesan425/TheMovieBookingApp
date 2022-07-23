package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginDelegate

class ViewPagerEntryTypeListViewHolder(itemView: View, private val mDelegate: LoginDelegate) :
    RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            mDelegate.onTapConfirm()
        }
    }
}