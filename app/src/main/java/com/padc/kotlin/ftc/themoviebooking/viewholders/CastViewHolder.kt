package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.kotlin.ftc.themoviebooking.data.vos.ActorVO
import com.padc.kotlin.ftc.themoviebooking.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_cast.view.*

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(actorVO: ActorVO) {
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${actorVO.profilePath}")
            .into(itemView.ivCast)
    }
}