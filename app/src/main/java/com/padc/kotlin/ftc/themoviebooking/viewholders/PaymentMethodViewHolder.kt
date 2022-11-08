package com.padc.kotlin.ftc.themoviebooking.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.PaymentVO
import com.padc.kotlin.ftc.themoviebooking.delegates.PaymentMethodDelegate
import kotlinx.android.synthetic.main.view_holder_payment_method.view.*


class PaymentMethodViewHolder(
    itemView: View,
    private val mDelegate: PaymentMethodDelegate,
    private var paymentId: Int? = 0
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            mDelegate.onTapMethod(
                paymentId
            )
        }
    }

    fun bindData(paymentVO: PaymentVO) {
        if (paymentVO.isSelected) {
            paymentId = paymentVO.id
            itemView.tvCardType.text = paymentVO.name
            itemView.tvCardName.text = paymentVO.description
            itemView.tvCardType.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorAccent
                )
            )
            itemView.tvCardName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorAccent
                )
            )

            itemView.ivCard.backgroundTintList =
                ContextCompat.getColorStateList(itemView.context, R.color.colorAccent)

        } else {
            paymentId = paymentVO.id
            itemView.tvCardType.text = paymentVO.name
            itemView.tvCardName.text = paymentVO.description

            itemView.tvCardType.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
            )
            itemView.tvCardName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorSecondaryText
                )
            )

            itemView.ivCard.backgroundTintList =
                ContextCompat.getColorStateList(itemView.context, R.color.colorSecondaryText)
        }
    }
}