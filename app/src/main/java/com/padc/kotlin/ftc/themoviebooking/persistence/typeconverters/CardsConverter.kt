package com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themoviebooking.data.vos.CardVO

class CardsConverter {
    @TypeConverter
    fun toString(cards: List<CardVO>?): String {
        return Gson().toJson(cards)
    }

    @TypeConverter
    fun toCardList(cardsJsonStr: String): List<CardVO>? {
        val cardsType = object : TypeToken<List<CardVO>?>() {}.type
        return Gson().fromJson(cardsJsonStr, cardsType)
    }
}