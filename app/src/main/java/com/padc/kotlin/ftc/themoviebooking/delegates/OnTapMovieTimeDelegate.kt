package com.padc.kotlin.ftc.themoviebooking.delegates

interface OnTapMovieTimeDelegate {
    fun onTapMovieTime(timeslotId: Int?, startTime: String?, cinemaName: String?, cinemaId: Int?)
}