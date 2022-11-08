package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "seating_plan")
data class SeatingPlanVO(

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String?,

    @SerializedName("seat_name")
    @ColumnInfo(name = "seat_name")
    val seatName: String?,

    @SerializedName("symbol")
    @ColumnInfo(name = "symbol")
    val symbol: String?,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Int?,

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean = false

) {
    fun isMovieSeatAvailable(): Boolean {
        return type == SEAT_TYPE_AVAILABLE
    }

    fun isMovieSeatTAKEN(): Boolean {
        return type == SEAT_TYPE_TAKEN
    }

    fun isMovieSeatRowTitle(): Boolean {
        return type == SEAT_TYPE_TEXT
    }

}

const val SEAT_TYPE_AVAILABLE = "available"
const val SEAT_TYPE_TAKEN = "taken"
const val SEAT_TYPE_TEXT = "text"
const val SEAT_TYPE_EMPTY = "space"

