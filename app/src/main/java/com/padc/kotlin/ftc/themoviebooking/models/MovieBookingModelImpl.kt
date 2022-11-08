package com.padc.kotlin.ftc.themoviebooking.models

import android.content.Context
import com.padc.kotlin.ftc.themoviebooking.data.vos.*
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.MovieDataAgent
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.TheMovieAppRetrofitDataAgentImpl
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.TheMovieBookingDataAgent
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.TheMovieBookingRetrofitDataAgentImpl
import com.padc.kotlin.ftc.themoviebooking.persistence.MovieDatabase
import com.padc.kotlin.ftc.themoviebooking.utils.PARAM_BEARER

object MovieBookingModelImpl : MovieBookingModel {

    private var savedToken: String? = null
    private const val SUCCESS_REGISTER_CODE: Int = 201
    private const val API_SUCCESS_CODE: Int = 200

    //private var mAuthToken: String? = null
    private val mTheMovieBookingDataAgent: TheMovieBookingDataAgent =
        TheMovieBookingRetrofitDataAgentImpl
    private val mMovieDataAgent: MovieDataAgent = TheMovieAppRetrofitDataAgentImpl

    private var mMovieDatabase: MovieDatabase? = null

    fun initDatabase(context: Context) {
        mMovieDatabase = MovieDatabase.getDBInstance(context)
    }

    override fun registerWithEmail(
        name: String?,
        email: String?,
        password: String?,
        phone: String?,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.registerWithEmail(
            name = name,
            email = email,
            password = password,
            phone = phone,
            onSuccess = { code, token, registeredUser ->
                //mAuthToken = token
                if (code == SUCCESS_REGISTER_CODE) {
                    onSuccess(true)
                    registeredUser.token = token
                    mMovieDatabase?.userDao()?.insertUser(registeredUser)
                }
            },
            onFailure = onFailure
        )
    }

    override fun loginWithEmail(
        email: String?,
        password: String?,
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.loginWithEmail(
            email = email,
            password = password,
            onSuccess = { code, token, registeredUser ->
                //mAuthToken = token
                if (code == API_SUCCESS_CODE) {
                    onSuccess(true)
                    registeredUser.token = token
                    mMovieDatabase?.userDao()?.insertUser(registeredUser)
                }
            },
            onFailure = onFailure
        )
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //get from persistence layer
        onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = NOW_SHOWING) ?: listOf())

        mMovieDataAgent.getNowPlayingMovies(
            onSuccess = {

                //save to persistence
                it.forEach { movie ->
                    movie.type = NOW_SHOWING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it)

                //network data - set up view layer
                onSuccess(it)
            },
            onFailure = onFailure
        )

    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        //get from persistence layer
        onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = COMING_SOON) ?: listOf())

        mMovieDataAgent.getComingSoonMovies(
            onSuccess = {

                //save to persistence
                it.forEach { movie ->
                    movie.type = COMING_SOON
                }
                mMovieDatabase?.movieDao()?.insertMovies(it)

                //network data - set up view layer
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun logOut(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingDataAgent.logOut(
            authToken = "$PARAM_BEARER $savedToken",
            onSuccess = { code, message ->
                if (code == API_SUCCESS_CODE) {
                    onSuccess(message)
                    savedToken = null
                    //mMovieDatabase?.userDao()?.deleteToken()

                }
            },
            onFailure = onFailure
        )
    }

    override fun getProfile(onSuccess: (RegisteredUserVO) -> Unit, onFailure: (String) -> Unit) {

        savedToken = mMovieDatabase?.userDao()?.getToken()

        mMovieDatabase?.userDao()?.getToken().let {

            mTheMovieBookingDataAgent.getProfile(
                authToken = "$PARAM_BEARER $savedToken",
                onSuccess = { code, registeredUserVO ->
                    if (code == API_SUCCESS_CODE) {
                        registeredUserVO.token = savedToken
                        onSuccess(registeredUserVO)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val tapedMovieFromDataBase =
            mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        tapedMovieFromDataBase?.let {
            onSuccess(it)
        }

        mMovieDataAgent.getMovieDetail(
            movieId = movieId,
            onSuccess = {

                //get selected movie
                val movieFromDataBase =
                    mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())

                //set and save database's movie type to network's movie type
                it.type = movieFromDataBase?.type

                //save to persistence layer
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

                //set up to view layer
                onSuccess(it)

            },
            onFailure = onFailure
        )
    }

    override fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getActors(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getCinemaTimeSlot(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaTimeSlotVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDatabase?.dateCinemaAndTimeSlotDao()?.getAllCinemaTimeSlots()?.let { onSuccess(it) }

        mMovieDatabase?.userDao()?.getToken().let {
            mTheMovieBookingDataAgent.getCinemaTimeSlot(
                authToken = "$PARAM_BEARER $it",
                movieId = movieId,
                date = date,
                onSuccess = { code, cinemaTimeSlots ->

                    var entityToSave: DateCinemaAndTimeSlotVO? = null

                    if (code == API_SUCCESS_CODE)
                        entityToSave = DateCinemaAndTimeSlotVO(
                            date = date,
                            cinemas = cinemaTimeSlots
                        )
                    entityToSave?.let { dateCinemaAndTimeSlotVO ->
                        mMovieDatabase?.dateCinemaAndTimeSlotDao()?.insertCinemaTimeSlots(
                            dateCinemaAndTimeSlotVO
                        )
                    }


                    onSuccess(cinemaTimeSlots)
                },
                onFailure = onFailure
            )
        }
    }

    override fun getSeatingPlan(
        timeSlotId: Int?,
        bookingDate: String?,
        onSuccess: (List<SeatingPlanVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDatabase?.userDao()?.getToken().let {
            mTheMovieBookingDataAgent.getSeatingPlan(
                authToken = "$PARAM_BEARER $it",
                timeSlotId = timeSlotId, bookingDate = bookingDate,
                onSuccess = { code, SeatingPlanVOList ->
                    if (code == API_SUCCESS_CODE) {
                        onSuccess(SeatingPlanVOList)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    override fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mMovieDatabase?.snackDao()?.getAllSnacks() ?: listOf())

        mMovieDatabase?.userDao()?.getToken().let {

            mTheMovieBookingDataAgent.getSnackList(
                authToken = "$PARAM_BEARER $it",
                onSuccess = { code, snackList ->
                    if (code == API_SUCCESS_CODE) {

                        mMovieDatabase?.snackDao()?.insertSnacks(snackList)

                        onSuccess(snackList)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    override fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        onSuccess(mMovieDatabase?.paymentDao()?.getAllPayments() ?: listOf())

        mMovieDatabase?.userDao()?.getToken().let {
            mTheMovieBookingDataAgent.getPaymentMethods(
                authToken = "$PARAM_BEARER $it",
                onSuccess = { code, message, paymentMethods ->
                    if (code == API_SUCCESS_CODE) {

                        mMovieDatabase?.paymentDao()?.insertPayments(paymentMethods)

                        onSuccess(paymentMethods)
                    } else {
                        onFailure(message)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    override fun createCard(
        cardNumber: String?,
        cardHolder: String?,
        expirationDate: String?,
        cvc: Int?,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        mMovieDatabase?.userDao()?.getToken().let {

            mTheMovieBookingDataAgent.createCard(
                authToken = "$PARAM_BEARER $it",
                cardNumber = cardNumber,
                cardHolder = cardHolder,
                expirationDate = expirationDate,
                cvc = cvc,
                onSuccess = { code, message, cardList ->
                    if (code == API_SUCCESS_CODE) {
                        onSuccess(cardList)
                    } else {
                        onFailure(message)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    override fun checkout(
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
    ) {
        mMovieDatabase?.userDao()?.getToken().let {

            val checkoutRequest = CheckoutRequest(
                timeSlotId = timeSlotId,
                row = row,
                seatNumber = seatNumber,
                bookingDate = bookingDate,
                totalPrice = totalPrice,
                movieId = movieId,
                cardId = cardId,
                cinemaId = cinemaId,
                snacks = snacks
            )

            mTheMovieBookingDataAgent.checkout(
                authToken = "$PARAM_BEARER $it",
                checkoutRequest,
                onSuccess = { code, checkoutVO ->
                    if (code == API_SUCCESS_CODE) {
                        onSuccess(checkoutVO)
                    }
                },
                onFailure = onFailure
            )
        }
    }


}