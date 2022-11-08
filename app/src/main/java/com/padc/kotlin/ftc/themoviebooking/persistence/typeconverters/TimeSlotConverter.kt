package com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotVO

class TimeSlotConverter {
    @TypeConverter
    fun toString(timeSlots: List<TimeSlotVO>?): String {
        return Gson().toJson(timeSlots)
    }

    @TypeConverter
    fun toTimeSlotList(timeSlotJsonStr: String): List<TimeSlotVO>? {
        val cardsType = object : TypeToken<List<TimeSlotVO>?>() {}.type
        return Gson().fromJson(timeSlotJsonStr, cardsType)
    }
}