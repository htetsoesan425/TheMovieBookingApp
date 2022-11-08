package com.padc.kotlin.ftc.themoviebooking.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themoviebooking.data.vos.ActorVO

data class GetActorsResponse(
    @SerializedName("results")
    val result: List<ActorVO>
)
