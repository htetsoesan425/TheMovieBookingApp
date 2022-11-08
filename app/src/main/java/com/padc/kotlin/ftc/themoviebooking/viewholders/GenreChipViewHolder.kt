package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.data.vos.GenreVO
import kotlinx.android.synthetic.main.view_holder_genre_chip.view.*

class GenreChipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(genreVO: GenreVO) {
        itemView.chipGenre.text = genreVO.name
    }
}