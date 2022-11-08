package com.padc.kotlin.ftc.themoviebooking.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themoviebooking.data.vos.PaymentVO

data class PaymentMethodsResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<PaymentVO>?,
)