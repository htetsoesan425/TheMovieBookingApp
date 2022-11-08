package com.padc.kotlin.ftc.themoviebooking.network.dataagents

import android.util.Log
import com.padc.kotlin.ftc.themoviebooking.data.vos.*
import com.padc.kotlin.ftc.themoviebooking.network.TheMovieBookingAPI
import com.padc.kotlin.ftc.themoviebooking.network.responses.*
import com.padc.kotlin.ftc.themoviebooking.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object TheMovieBookingRetrofitDataAgentImpl : TheMovieBookingDataAgent {

    private var mTheMovieBookingAPI: TheMovieBookingAPI? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor = interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieBookingAPI = retrofit.create(TheMovieBookingAPI::class.java)
    }

    override fun registerWithEmail(
        name: String?,
        email: String?,
        password: String?,
        phone: String?,
        onSuccess: (Int, String, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.registerWithEmail(
            name = name,
            email = email,
            password = password,
            phone = phone
        )?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    val token = response.body()?.token ?: ""
                    val registeredUserVO = response.body()?.data
                    registeredUserVO?.let {
                        onSuccess(responseCode, token, registeredUserVO)
                    }
                } else {
                    Log.d("TAG", "onResponse: " + response.message())
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun loginWithEmail(
        email: String?,
        password: String?,
        onSuccess: (Int, String, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.loginWithEmail(
            email = email,
            password = password,
        )?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    val token = response.body()?.token ?: ""
                    val registeredUserVO = response.body()?.data
                    registeredUserVO?.let {
                        onSuccess(responseCode, token, registeredUserVO)
                    }
                } else {
                    Log.d("TAG", "onResponse: " + response.message())
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun logOut(
        authToken: String?,
        onSuccess: (Int, String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.logOut(authToken)?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    val responseMsg = response.body()?.message ?: ""
                    onSuccess(responseCode, responseMsg)
                } else {
                    Log.d("TAG", "onResponse: " + response.message())
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFailure(t.message ?: "")

            }

        })
    }

    override fun getProfile(
        authToken: String?,
        onSuccess: (Int, RegisteredUserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.getProfile(authToken)?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    val registeredUserVO = response.body()?.data
                    registeredUserVO?.let {
                        onSuccess(responseCode, registeredUserVO)
                    }
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFailure(t.message ?: "")

            }

        })
    }

    override fun getCinemaTimeSlot(
        authToken: String?,
        movieId: String?,
        date: String?,
        onSuccess: (Int, List<CinemaTimeSlotVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.getCinemaDayTimeSlot(
            token = authToken,
            movieID = movieId,
            date = date
        )
            ?.enqueue(object : Callback<GetCinemaTimeSlotResponse> {
                override fun onResponse(
                    call: Call<GetCinemaTimeSlotResponse>,
                    response: Response<GetCinemaTimeSlotResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseCode = response.body()?.code ?: 0
                        val cinemaTimeSlotVOList = response.body()?.data
                        Log.d("TAG", "onResponse: ${response.message()}")
                        cinemaTimeSlotVOList?.let {
                            onSuccess(responseCode, cinemaTimeSlotVOList)
                        }
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<GetCinemaTimeSlotResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getSeatingPlan(
        authToken: String?,
        timeSlotId: Int?,
        bookingDate: String?,
        onSuccess: (Int, List<SeatingPlanVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.getSeatingPlan(authToken, timeSlotId, bookingDate)
            ?.enqueue(object : Callback<SeatingPlanResponse> {
                override fun onResponse(
                    call: Call<SeatingPlanResponse>,
                    response: Response<SeatingPlanResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseCode = response.body()?.code ?: 0
                        val seatingPlanVOList = response.body()?.data
                        seatingPlanVOList?.let {
                            onSuccess(responseCode, it.flatten())
                        }
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<SeatingPlanResponse>, t: Throwable) {
                    onFailure(t.message ?: "")

                }

            })
    }

    override fun getSnackList(
        authToken: String?,
        onSuccess: (Int, List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.getSnackList(authToken)
            ?.enqueue(object : Callback<GetSnackListResponse> {
                override fun onResponse(
                    call: Call<GetSnackListResponse>,
                    response: Response<GetSnackListResponse>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code ?: 0
                        val snackVOList = response.body()?.data ?: listOf()
                        onSuccess(code, snackVOList)
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<GetSnackListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    override fun getPaymentMethods(
        authToken: String?,
        onSuccess: (Int, String, List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.getPaymentMethods(authToken)
            ?.enqueue(object : Callback<PaymentMethodsResponse> {
                override fun onResponse(
                    call: Call<PaymentMethodsResponse>,
                    response: Response<PaymentMethodsResponse>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code ?: 0
                        val message = response.body()?.message ?: ""
                        val paymentMethods = response.body()?.data ?: listOf()
                        onSuccess(code, message, paymentMethods)
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<PaymentMethodsResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun createCard(
        authToken: String?,
        cardNumber: String?,
        cardHolder: String?,
        expirationDate: String?,
        cvc: Int?,
        onSuccess: (Int, String, List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.createCard(
            authToken,
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            expirationDate = expirationDate,
            cvc = cvc
        )
            ?.enqueue(object : Callback<CreateCardResponse> {
                override fun onResponse(
                    call: Call<CreateCardResponse>,
                    response: Response<CreateCardResponse>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code ?: 0
                        val message = response.body()?.message ?: ""
                        val cardList = response.body()?.data ?: listOf()
                        onSuccess(code, message, cardList)
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CreateCardResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun checkout(
        authToken: String?,
        checkoutRequest: CheckoutRequest,
        onSuccess: (Int, CheckoutVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingAPI?.checkout(
            authToken,
            checkoutRequest
        )
            ?.enqueue(object : Callback<CheckoutResponse> {
                override fun onResponse(
                    call: Call<CheckoutResponse>,
                    response: Response<CheckoutResponse>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code ?: 0
                        val message = response.body()?.message ?: ""
                        val checkoutVO = response.body()?.data

                        if (checkoutVO != null) {
                            onSuccess(code, checkoutVO)
                        } else {
                            onFailure(message)
                        }

                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }


}