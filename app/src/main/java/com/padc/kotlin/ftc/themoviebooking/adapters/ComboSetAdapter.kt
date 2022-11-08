package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.SnackVO
import com.padc.kotlin.ftc.themoviebooking.delegates.SnackDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.ComboSetViewHolder

class ComboSetAdapter(private val mDelegate: SnackDelegate) :
    RecyclerView.Adapter<ComboSetViewHolder>() {
    private var mSnackVOList: List<SnackVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComboSetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_combo_set, parent, false)
        return ComboSetViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: ComboSetViewHolder, position: Int) {
        if (mSnackVOList.isNotEmpty()) {
            holder.bindData(mSnackVOList[position])
        }
    }

    override fun getItemCount(): Int = mSnackVOList.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackVOList: List<SnackVO>) {
        mSnackVOList = snackVOList
        notifyDataSetChanged()
    }
}