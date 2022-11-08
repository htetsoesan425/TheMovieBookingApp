package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class TimeSlotVO(

    @SerializedName("start_time")
    @ColumnInfo(name = "start_time")
    val startTime: String?,

    @SerializedName("cinema_day_timeslot_id")
    @ColumnInfo(name = "cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean = false

)