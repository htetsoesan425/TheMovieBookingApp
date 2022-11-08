package com.padc.kotlin.ftc.themoviebooking.delegates

interface PaymentMethodDelegate {
    fun onTapMethod(
        paymentId: Int?
    )
}