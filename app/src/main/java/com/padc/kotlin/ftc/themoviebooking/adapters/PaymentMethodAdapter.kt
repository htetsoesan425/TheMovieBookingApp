package com.padc.kotlin.ftc.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.PaymentVO
import com.padc.kotlin.ftc.themoviebooking.delegates.PaymentMethodDelegate
import com.padc.kotlin.ftc.themoviebooking.viewholders.PaymentMethodViewHolder

class PaymentMethodAdapter(private val mDelegate: PaymentMethodDelegate) :
    RecyclerView.Adapter<PaymentMethodViewHolder>() {
    private var mPaymentMethods: List<PaymentVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_payment_method, parent, false)
        return PaymentMethodViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        if (mPaymentMethods.isNotEmpty()) {
            holder.bindData(mPaymentMethods[position])
        }
    }

    override fun getItemCount(): Int = mPaymentMethods.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(paymentMethods: List<PaymentVO>) {
        mPaymentMethods = paymentMethods
        notifyDataSetChanged()
    }
}