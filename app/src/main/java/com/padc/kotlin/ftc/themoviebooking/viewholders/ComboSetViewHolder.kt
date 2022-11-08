package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.data.vos.SnackVO
import com.padc.kotlin.ftc.themoviebooking.delegates.SnackDelegate
import kotlinx.android.synthetic.main.view_holder_combo_set.view.*

class ComboSetViewHolder(itemView: View, mDelegate: SnackDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mSnackVO: SnackVO? = null

    init {
        itemView.tvPlus.setOnClickListener {
            mDelegate.onTapPlus(mSnackVO?.id ?: 0)
        }
        itemView.tvMinus.setOnClickListener {
            mDelegate.onTapMinus(mSnackVO?.id ?: 0)
        }

    }

    fun bindData(snackVO: SnackVO) {
        mSnackVO = snackVO
        itemView.tvComboSetName.text = snackVO.name
        itemView.tvPrice.text = "${snackVO.totalPrice}\$"
        itemView.tvDescription.text = snackVO.description
        itemView.tvCount.text = snackVO.quantity.toString()
    }
}