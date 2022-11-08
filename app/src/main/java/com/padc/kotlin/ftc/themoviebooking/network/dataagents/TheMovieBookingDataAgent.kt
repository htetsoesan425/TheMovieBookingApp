package com.padc.kotlin.ftc.themoviebooking.network.dataagents

import com.padc.kotlin.ftc.themoviebooking.data.vos.*


interface TheMovieBookingDataAgent {
    fun registerWithEmail(
        name: String?,
        email: String?,
        password: String?,
        phone: String?,
        onSuccess: (Int, String, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun loginWithEmail(
        email: String?,
        password: String?,
        onSuccess: (Int, String, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun logOut(
        authToken: String?,
        onSuccess: (Int, String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getProfile(
        authToken: String?,
        onSuccess: (Int, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaTimeSlot(
        authToken: String?,
        movieId: String?,
        date: String?,
        onSuccess: (Int, List<CinemaTimeSlotVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSeatingPlan(
        authToken: String?,
        timeSlotId: Int?,
        bookingDate: String?,
        onSuccess: (Int, List<SeatingPlanVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        authToken: String?,
        onSuccess: (Int, List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        authToken: String?,
        onSuccess: (Int, String, List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createCard(
        authToken: String?,
        cardNumber: String?,
        cardHolder: String?,
        expirationDate: String?,
        cvc: Int?,
        onSuccess: (Int, String, List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkout(
        authToken: String?,
        checkoutRequest: CheckoutRequest,
        onSuccess: (Int, CheckoutVO) -> Unit,
        onFailure: (String) -> Unit
    )
}