package com.padc.kotlin.ftc.themoviebooking.network.dataagents

interface SeatingPlanDelegate {
    fun onTapSeat(
        id: Int?,
        symbol: String?
    )
}