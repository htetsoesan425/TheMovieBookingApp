package com.padc.kotlin.ftc.themoviebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.viewholders.GenreChipViewHolder

class GenreChipAdapter : RecyclerView.Adapter<GenreChipViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreChipViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_genre_chip, parent, false)
        return GenreChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreChipViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 2
    }
}