package com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO

class CinemaDateAndTimeSlotTypeConverter {
    @TypeConverter
    fun toString(cinemaAndTimeSlots: List<CinemaTimeSlotVO>?): String {
        return Gson().toJson(cinemaAndTimeSlots)
    }

    @TypeConverter
    fun toCinemaAndTimeSlots(cinemaAndTimeSlotJsonStr: String): List<CinemaTimeSlotVO>? {
        val cardsType = object : TypeToken<List<CinemaTimeSlotVO>?>() {}.type
        return Gson().fromJson(cinemaAndTimeSlotJsonStr, cardsType)
    }
}