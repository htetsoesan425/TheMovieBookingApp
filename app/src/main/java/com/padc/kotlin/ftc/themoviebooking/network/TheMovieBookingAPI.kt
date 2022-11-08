package com.padc.kotlin.ftc.themoviebooking.network

import com.padc.kotlin.ftc.themoviebooking.data.vos.CheckoutRequest
import com.padc.kotlin.ftc.themoviebooking.network.responses.*
import com.padc.kotlin.ftc.themoviebooking.utils.*
import retrofit2.Call
import retrofit2.http.*

interface TheMovieBookingAPI {

    @FormUrlEncoded
    @POST(API_REGISTER_WITH_EMAIL)
    fun registerWithEmail(
        @Field(PARAM_NAME) name: String?,
        @Field(PARAM_EMAIL) email: String?,
        @Field(PARAM_PASSWORD) password: String?,
        @Field(PARAM_PHONE) phone: String?
    ): Call<UserResponse>

    @POST(API_LOGIN_WITH_REGISTER)
    fun loginWithEmail(
        @Query(PARAM_EMAIL) email: String?,
        @Query(PARAM_PASSWORD) password: String?
    ): Call<UserResponse>

    @POST(API_LOG_OUT)
    fun logOut(
        @Header(PARAM_AUTHORIZATION) token: String?,
    ): Call<UserResponse>

    @GET(API_PROFILE)
    fun getProfile(
        @Header(PARAM_AUTHORIZATION) token: String?,
    ): Call<UserResponse>

    @GET(API_GET_CINEMA_DAY_TIME_SLOT)
    fun getCinemaDayTimeSlot(
        @Header(PARAM_AUTHORIZATION) token: String?,
        @Query(PARAM_MOVIE_ID) movieID: String?,
        @Query(PARAM_DATE) date: String?,
    ): Call<GetCinemaTimeSlotResponse>

    @GET(API_GET_SEAT_PLAN)
    fun getSeatingPlan(
        @Header(PARAM_AUTHORIZATION) token: String?,
        @Query(PARAM_TIMESLOT_ID) timeSlotId: Int?,
        @Query(PARAM_BOOKING_DATE) bookingDate: String?,
    ): Call<SeatingPlanResponse>

    @GET(API_GET_SNACK_LIST)
    fun getSnackList(
        @Header(PARAM_AUTHORIZATION) token: String?,
    ): Call<GetSnackListResponse>

    @GET(API_GET_PAYMENT_METHODS)
    fun getPaymentMethods(
        @Header(PARAM_AUTHORIZATION) token: String?,
    ): Call<PaymentMethodsResponse>

    @FormUrlEncoded
    @POST(API_CREATE_CARD)
    fun createCardField(
        @Header(PARAM_AUTHORIZATION) token: String?,
        @Field(PARAM_CARD_NUMBER) cardNumber: String?,
        @Field(PARAM_CARD_HOLDER) cardHolder: String?,
        @Field(PARAM_EXPIRATION_DATE) expirationDate: String?,
        @Field(PARAM_CVC) cvc: Int?
    ): Call<CreateCardResponse>

    @FormUrlEncoded
    @POST(API_CREATE_CARD)
    fun createCard(
        @Header(PARAM_AUTHORIZATION) token: String?,
        @Field(PARAM_CARD_NUMBER) cardNumber: String?,
        @Field(PARAM_CARD_HOLDER) cardHolder: String?,
        @Field(PARAM_EXPIRATION_DATE) expirationDate: String?,
        @Field(PARAM_CVC) cvc: Int?
    ): Call<CreateCardResponse>

    @POST(API_CHECKOUT)
    fun checkout(
        @Header(PARAM_AUTHORIZATION) token: String?,
        @Body checkoutRequest: CheckoutRequest
    ): Call<CheckoutResponse>

}