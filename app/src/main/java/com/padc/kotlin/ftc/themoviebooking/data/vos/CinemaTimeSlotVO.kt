package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters.TimeSlotConverter

@TypeConverters(
    TimeSlotConverter::class
)
data class CinemaTimeSlotVO(

    @SerializedName("cinema_id")
    @ColumnInfo(name = "cinema_id")
    val cinemaId: Int?,

    @SerializedName("date")
    @ColumnInfo(name = "date")
    val date: String?,

    @SerializedName("cinema")
    @ColumnInfo(name = "cinema")
    val cinema: String?,

    @SerializedName("timeslots")
    @ColumnInfo(name = "timeslots")
    val timeslots: List<TimeSlotVO>? = null,

    )