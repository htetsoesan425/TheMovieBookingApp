package com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO

class CinemasTypesConverter {
    @TypeConverter
    fun toString(cinemaTimeSlots: List<CinemaTimeSlotVO>?): String {
        return Gson().toJson(cinemaTimeSlots)
    }

    @TypeConverter
    fun toTimeSlotList(cinemaTimeSlotJsonStr: String): List<CinemaTimeSlotVO>? {
        val cinemaTimeSlotType = object : TypeToken<List<CinemaTimeSlotVO>?>() {}.type
        return Gson().fromJson(cinemaTimeSlotJsonStr, cinemaTimeSlotType)
    }
}