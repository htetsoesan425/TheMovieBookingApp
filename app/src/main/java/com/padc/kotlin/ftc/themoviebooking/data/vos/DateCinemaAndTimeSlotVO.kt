package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters.CinemaDateAndTimeSlotTypeConverter

@Entity(tableName = "cinema_time_slots")
@TypeConverters(
    CinemaDateAndTimeSlotTypeConverter::class
)

data class DateCinemaAndTimeSlotVO(

    @ColumnInfo(name = "date")
    @PrimaryKey
    var date: String = "",

    @ColumnInfo(name = "cinemas")
    var cinemas: List<CinemaTimeSlotVO>?,

    )