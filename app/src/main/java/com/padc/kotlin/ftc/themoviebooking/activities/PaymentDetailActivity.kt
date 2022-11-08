package com.padc.kotlin.ftc.themoviebooking.activities

import CardCarouselAdapter
import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.CardVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.RegisteredUserVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.SelectedSnackVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.SnackVO
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_payment_detail.*


class PaymentDetailActivity : AppCompatActivity() {

    private var mCinemaName: String? = ""
    private var mSelectedCardId: Int? = 0
    private var totalAmount: Int? = 0
    private var timeSlotId: Int? = 0
    private var row: String? = ""
    private var seatNumber: String? = ""
    private var bookingDate: String? = ""
    private var movieId: Int? = 0
    private var cinemaId: Int? = 0
    private var snacks: List<SelectedSnackVO> = mutableListOf()
    private var snacksStr: String? = ""
    lateinit var mCardCarouselAdapter: CardCarouselAdapter
    private var mCardList: List<CardVO> = listOf()
    private lateinit var carousel: Carousel

    private var tempSnackVO: SnackVO? = null

    //model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl


    companion object {
        private const val IE_TIME_SLOT_IT = "IE_TIME_SLOT_IT"
        private const val IE_ROW = "IE_ROW"
        private const val IE_SEAT = "IE_SEAT"
        private const val IE_BOOKING_DATE = "IE_BOOKING_DATE"
        private const val IE_MOVIE_ID = "IE_MOVIE_ID"
        private const val IE_CARD_ID = "IE_CARD_ID"
        private const val IE_CINEMA_ID = "IE_CINEMA_ID"
        private const val IE_CINEMA_NAME = "IE_CINEMA_NAME"
        private const val IE_SNACKS = "IE_SNACKS"
        private const val IE_TOTAL_AMOUNT = "IE_TOTAL_AMOUNT"

        fun newIntent(
            context: Context,
            movieId: Int?,
            cinemaId: Int?,
            bookingDate: String?,
            timeSlotId: Int?,
            rows: String?,
            seats: String?,
            cinemaName: String?,
            snacks: String,
            totalAmount: Int?
        ): Intent {
            val intent = Intent(context, PaymentDetailActivity::class.java)

            intent.putExtra(IE_MOVIE_ID, movieId)
            intent.putExtra(IE_CINEMA_ID, cinemaId)
            intent.putExtra(IE_BOOKING_DATE, bookingDate)
            intent.putExtra(IE_TIME_SLOT_IT, timeSlotId)
            intent.putExtra(IE_ROW, rows)
            intent.putExtra(IE_SEAT, seats)
            intent.putExtra(IE_CINEMA_NAME, cinemaName)
            intent.putExtra(IE_SNACKS, snacks)
            intent.putExtra(IE_TOTAL_AMOUNT, totalAmount)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)

        getIntentExtraValues()

        tvPaymentAmount.text = "$$totalAmount"

        setUpCarousel()
        requestData()
        setUpClickListeners()
    }

    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                bindData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }


    private fun bindData(registeredUserVO: RegisteredUserVO) {
        registeredUserVO.cards?.let {
            mCardList = it
            mCardCarouselAdapter.setData(it.reversed())
        }
    }


    private fun getIntentExtraValues() {
        movieId = intent?.getIntExtra(IE_MOVIE_ID, 0)
        cinemaId = intent?.getIntExtra(IE_CINEMA_ID, 0)
        timeSlotId = intent?.getIntExtra(IE_TIME_SLOT_IT, 0)
        bookingDate = intent?.getStringExtra(IE_BOOKING_DATE)
        row = intent?.getStringExtra(IE_ROW)
        seatNumber = intent?.getStringExtra(IE_SEAT)
        totalAmount = intent?.getIntExtra(IE_TOTAL_AMOUNT, 0)
        mCinemaName = intent?.getStringExtra(IE_CINEMA_NAME)

        snacksStr = intent?.getStringExtra(IE_SNACKS)

        snacks = Gson().fromJson(snacksStr, mutableListOf<SelectedSnackVO>().javaClass)


        Log.d("TAG", "getIntentExtraValues: $snacks")
    }

    private fun setUpClickListeners() {
        btnConfirm.setOnClickListener {

            if (mCardList.isNotEmpty()) {

                mMovieBookingModel.checkout(
                    timeSlotId = timeSlotId,
                    row = row,
                    seatNumber = seatNumber,
                    bookingDate = bookingDate,
                    totalPrice = totalAmount,
                    movieId = movieId,
                    cardId = mCardList[carousel.getCurrentPosition()].id ?: 0,
                    snacks = snacks,
                    cinemaId = cinemaId,
                    onSuccess = {

                        startActivity(
                            VoucherActivity.newIntent(
                                this,
                                cinemaName = mCinemaName,
                                checkoutVOString = Gson().toJson(it)
                            )
                        )
                    },
                    onFailure = {
                        showError(it)
                    }
                )

            } else {
                Snackbar.make(
                    window.decorView,
                    "Please add new card to checkout!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnAddNewCard.setOnClickListener {
            val i = Intent(this, AddNewCardActivity::class.java)
            startActivityForResult(i, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            requestData()
        }
    }

    private fun setUpCarousel() {
        mCardCarouselAdapter = CardCarouselAdapter()
        carousel = Carousel(this, carouselView, mCardCarouselAdapter)
        carousel.setOrientation(CarouselView.HORIZONTAL, false)
        carousel.autoScroll(false, 5000, false)
        carousel.scaleView(true)

        /*carousel.addCarouselListener(object : CarouselListener {
            override fun onPositionChange(position: Int) {
                mSelectedCardId = mCardList.reversed()[position].id
                mCardList.reversed()[position].isSelected = true
                mCardCarouselAdapter.setData(mCardList.reversed())
            }

            override fun onScroll(dx: Int, dy: Int) {
            }
        })*/

    }
}
