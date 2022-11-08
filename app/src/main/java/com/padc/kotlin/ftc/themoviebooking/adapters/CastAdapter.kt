package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.ActorVO
import com.padc.kotlin.ftc.themoviebooking.viewholders.CastViewHolder

class CastAdapter : RecyclerView.Adapter<CastViewHolder>() {
    private var mActors: List<ActorVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindData(mActors[position])
    }

    override fun getItemCount(): Int {
        return mActors.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(actors: List<ActorVO>) {
        mActors = actors
        notifyDataSetChanged()
    }
}