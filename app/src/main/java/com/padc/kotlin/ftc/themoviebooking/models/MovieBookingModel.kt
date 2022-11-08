package com.padc.kotlin.ftc.themoviebooking.models

import com.padc.kotlin.ftc.themoviebooking.data.vos.*


interface MovieBookingModel {

    fun registerWithEmail(
        name: String?,
        email: String?,
        password: String?,
        phone: String?,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    )

    fun loginWithEmail(
        email: String?,
        password: String?,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun logOut(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getProfile(
        onSuccess: (RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaTimeSlot(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaTimeSlotVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSeatingPlan(
        timeSlotId: Int?,
        bookingDate: String?,
        onSuccess: (List<SeatingPlanVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createCard(
        cardNumber: String?,
        cardHolder: String?,
        expirationDate: String?,
        cvc: Int?,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkout(
        timeSlotId: Int?,
        row: String?,
        seatNumber: String?,
        bookingDate: String?,
        totalPrice: Int?,
        movieId: Int?,
        cardId: Int?,
        cinemaId: Int?,
        snacks: List<SelectedSnackVO>?,
        onSuccess: (CheckoutVO) -> Unit,
        onFailure: (String) -> Unit

    )
}