package com.padc.kotlin.ftc.themoviebooking.viewpods

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate

class MovieViewHolder(itemView: View, private val mDelegate: MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            mDelegate.onTapMovie()
        }
    }
}