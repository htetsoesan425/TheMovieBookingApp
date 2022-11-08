package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.ComboSetAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.PaymentMethodAdapter
import com.padc.kotlin.ftc.themoviebooking.data.vos.PaymentVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.SelectedSnackVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.SnackVO
import com.padc.kotlin.ftc.themoviebooking.delegates.PaymentMethodDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.SnackDelegate
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_movie_time.btnBack
import kotlinx.android.synthetic.main.activity_snack.*

class SnackActivity : AppCompatActivity(), SnackDelegate, PaymentMethodDelegate {

    private var mSnacks: List<SnackVO> = listOf()
    private var ticketAmount: Int? = 0
    private var movieName: String? = ""
    private var movieDate: String? = ""
    private var cinemaName: String? = ""
    private var row: String? = ""
    private var selectedSeats: String? = ""
    private var movieId: Int? = 0
    private var cinemaId: Int? = 0
    private var timeSlotId: Int? = 0
    private var totalAmount: Int? = 0
    private var mPaymentMethods: List<PaymentVO>? = listOf()
    private lateinit var mComboSetAdapter: ComboSetAdapter
    private lateinit var mPaymentMethodAdapter: PaymentMethodAdapter
    var snackCount: Int = 0
    private var subTotal: Int? = 0
    var isChosen: Boolean = false
    var mSelectedSnacks: ArrayList<SelectedSnackVO> = arrayListOf()
    //private val selectedSnackVO: SelectedSnackVO? = null


    //model
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {
        private const val IE_TOTAL_AMOUNT = "IE_TICKET_AMOUNT"
        private const val IE_MOVIE_ID = "IE_MOVIE_ID"
        private const val IE_MOVIE_NAME = "IE_MOVIE_NAME"
        private const val IE_MOVIE_DATE = "IE_MOVIE_DATE"
        private const val IE_CINEMA_NAME = "IE_CINEMA_NAME"
        private const val IE_CINEMA_ID = "IE_CINEMA_ID"
        private const val IE_TIME_SLOT_ID = "IE_TIME_SLOT_ID_TIME"
        private const val IE_START_TIME = "IE_START_TIME"
        private const val IE_TICKET_AMOUNT = "IE_TICKET_AMOUNT"
        private const val IE_ROW = "IE_ROW"
        private const val IE_SEAT = "IE_SEAT"

        fun newIntent(
            context: Context,
            movieId: Int?,
            movieName: String?,
            movieDate: String?,
            cinemaName: String?,
            cinemaId: Int?,
            timeSlotId: Int?,
            ticketAmt: Int?,
            rows: String?,
            seats: String?
        ): Intent {
            val intent = Intent(context, SnackActivity::class.java)
            intent.putExtra(IE_MOVIE_ID, movieId)
            intent.putExtra(IE_MOVIE_NAME, movieName)
            intent.putExtra(IE_MOVIE_DATE, movieDate)
            intent.putExtra(IE_CINEMA_NAME, cinemaName)
            intent.putExtra(IE_CINEMA_ID, cinemaId)
            intent.putExtra(IE_TIME_SLOT_ID, timeSlotId)
            intent.putExtra(IE_TICKET_AMOUNT, ticketAmt)
            intent.putExtra(IE_ROW, rows)
            intent.putExtra(IE_SEAT, seats)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)
        getIntentExtraValues()
        setUpRecyclerView()
        setUpListener()
        requestData()
        btnPay.text = "Pay $ticketAmount$"
    }

    private fun getIntentExtraValues() {
        movieId = intent?.getIntExtra(IE_MOVIE_ID, 0)
        movieName = intent?.getStringExtra(IE_MOVIE_NAME)
        movieDate = intent?.getStringExtra(IE_MOVIE_DATE)
        cinemaName = intent?.getStringExtra(IE_CINEMA_NAME)
        timeSlotId = intent?.getIntExtra(IE_TIME_SLOT_ID, 0)
        cinemaId = intent?.getIntExtra(IE_CINEMA_ID, 0)
        ticketAmount = intent?.getIntExtra(IE_TICKET_AMOUNT, 0)
        row = intent?.getStringExtra(IE_ROW)
        selectedSeats = intent?.getStringExtra(IE_SEAT)
    }

    private fun requestData() {
        mMovieBookingModel.getSnackList(
            onSuccess = { snackVOList ->
                mSnacks = snackVOList
                mComboSetAdapter.setNewData(snackVOList)
            },
            onFailure = {
                showError(it)
            }
        )

        mMovieBookingModel.getPaymentMethods(
            onSuccess = { paymentMethods ->
                mPaymentMethods = paymentMethods
                mPaymentMethodAdapter.setNewData(paymentMethods)

            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        mComboSetAdapter = ComboSetAdapter(this)
        rvComboSet.adapter = mComboSetAdapter
        rvComboSet.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mPaymentMethodAdapter = PaymentMethodAdapter(this)
        rvPaymentMethod.adapter = mPaymentMethodAdapter
        rvPaymentMethod.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnPay.setOnClickListener {
            //Log.d("TAG", "mSelectedSnacks: ${Gson().toJson(mSelectedSnacks)}")

            if (isChosen) {
                startActivity(
                    PaymentDetailActivity.newIntent(
                        this,
                        movieId = movieId,
                        cinemaId = cinemaId,
                        bookingDate = movieDate,
                        timeSlotId = timeSlotId,
                        rows = row,
                        seats = selectedSeats,
                        cinemaName = cinemaName,
                        snacks = Gson().toJson(mSelectedSnacks),
                        totalAmount = totalAmount
                    )
                )
            } else {
                Snackbar.make(
                    window.decorView,
                    getString(R.string.lbl_choose_a_payment_method),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onTapMinus(snackId: Int) {
        for (snack in mSnacks) {
            if (snack.id == snackId) {
                snack.quantity = snack.quantity.minus(1)

                subTotal = snack.price?.let { subTotal?.minus(it) }

                snack.totalPrice -= snack.price ?: 0

                mComboSetAdapter.setNewData(mSnacks)

                if (snack.quantity > 0) {

                    /*mSelectedSnacks =
                        mSelectedSnacks.plus(SelectedSnackVO(snack.id, snack.quantity))*/

                    mSelectedSnacks.add(SelectedSnackVO(snack.id, snack.quantity))

                }

                setCalculatedData(subTotal)
            }
        }
    }

    override fun onTapPlus(snackId: Int) {

        // for (snack in mSnacks ) {
        for ((index, snack) in mSnacks.withIndex()) {
            if (snack.id == snackId) {
                snack.quantity = snack.quantity.plus(1)

                subTotal = snack.price?.let { subTotal?.plus(it) }

                snack.totalPrice += snack.price ?: 0

                mComboSetAdapter.setNewData(mSnacks)    //update list

                if (snack.quantity > 0) {
                    mSelectedSnacks.add(SelectedSnackVO(snack.id, snack.quantity))
                }


                setCalculatedData(subTotal)
            }
        }
    }


    private fun setCalculatedData(subTotal: Int?) {
        tvSubTotal.text = "$subTotal$"
        totalAmount = subTotal?.let { ticketAmount?.plus(it) }
        btnPay.text = "Pay $totalAmount$"
    }


    override fun onTapMethod(
        paymentId: Int?,
    ) {
        mPaymentMethods?.forEach { paymentMethod ->
            paymentMethod.isSelected = paymentMethod.id == paymentId
            isChosen = true
        }
        mPaymentMethods?.let {
            mPaymentMethodAdapter.setNewData(it)
        }
    }
}