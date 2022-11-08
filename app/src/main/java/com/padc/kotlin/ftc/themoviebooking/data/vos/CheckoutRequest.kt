package com.padc.kotlin.ftc.themoviebooking.data.vos

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themoviebooking.utils.*
import java.io.Serializable

data class CheckoutRequest(
    @SerializedName(PARAM_TIMESLOT_ID)
    var timeSlotId: Int?,
    @SerializedName(PARAM_ROW)
    var row: String?,
    @SerializedName(PARAM_SEAT_NUMBER)
    var seatNumber: String?,
    @SerializedName(PARAM_BOOKING_DATE)
    var bookingDate: String?,
    @SerializedName(PARAM_TOTAL_PRICE)
    var totalPrice: Int?,
    @SerializedName(PARAM_MOVIE_ID)
    var movieId: Int?,
    @SerializedName(PARAM_CARD_ID)
    var cardId: Int?,
    @SerializedName(PARAM_CINEMA_ID)
    var cinemaId: Int?,
    @SerializedName(PARAM_SNACKS)
    var snacks: List<SelectedSnackVO>?,

    ) : Serializable
