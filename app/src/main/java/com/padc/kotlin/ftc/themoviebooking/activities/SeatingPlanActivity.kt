package com.padc.kotlin.ftc.themoviebooking.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieSeatAdapter
import com.padc.kotlin.ftc.themoviebooking.data.vos.SeatingPlanVO
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import com.padc.kotlin.ftc.themoviebooking.network.dataagents.SeatingPlanDelegate
import kotlinx.android.synthetic.main.activity_seating_plan.*
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class SeatingPlanActivity : AppCompatActivity(), SeatingPlanDelegate {

    private var cinemaId: Int? = 0
    private var cinemaName: String? = ""
    private var mSeatingPlanVO: List<SeatingPlanVO>? = listOf()
    private val seatSelectedData = mutableListOf<String>()
    private val seatRowSelectedData = mutableListOf<String>()
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    private var totalTicketCount: Int = 0
    private var ticketAmt: Int? = 0

    private var movieId: Int? = 0
    private var movieName: String? = ""
    private var movieDate: String? = ""
    private var timeSlotId: Int? = 0
    private var startTime: String? = ""
    private var mSelectedSeats: String? = ""
    private var mSelectedRows: String? = ""

    private val mAdapter: MovieSeatAdapter = MovieSeatAdapter(this)

    companion object {

        private const val IE_MOVIE_ID = "IE_MOVIE_ID"
        private const val IE_MOVIE_NAME = "IE_MOVIE_NAME"
        private const val IE_MOVIE_DATE = "IE_MOVIE_DATE"
        private const val IE_CINEMA_NAME = "IE_CINEMA_NAME"
        private const val IE_CINEMA_ID = "IE_CINEMA_ID"
        private const val IE_TIME_SLOT_ID_TIME = "IE_TIME_SLOT_ID_TIME"
        private const val IE_START_TIME = "IE_START_TIME"

        fun newIntent(
            context: Context,
            movieId: Int?,
            movieName: String?,
            movieDate: String?,
            cinemaName: String?,
            cinemaId: Int?,
            timeslotId: Int?,
            startTime: String?
        ): Intent {
            val intent = Intent(context, SeatingPlanActivity::class.java)
            intent.putExtra(IE_MOVIE_ID, movieId)
            intent.putExtra(IE_MOVIE_NAME, movieName)
            intent.putExtra(IE_MOVIE_DATE, movieDate)
            intent.putExtra(IE_CINEMA_NAME, cinemaName)
            intent.putExtra(IE_CINEMA_ID, cinemaId)
            intent.putExtra(IE_TIME_SLOT_ID_TIME, timeslotId)
            intent.putExtra(IE_START_TIME, startTime)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seating_plan)
        getIntentExtraValues()
        setUpRecyclerView()
        setUpListener()
        requestData()
    }

    private fun getIntentExtraValues() {
        movieId = intent?.getIntExtra(IE_MOVIE_ID, 0)
        movieName = intent?.getStringExtra(IE_MOVIE_NAME)
        movieDate = intent?.getStringExtra(IE_MOVIE_DATE)
        cinemaName = intent?.getStringExtra(IE_CINEMA_NAME)
        cinemaId = intent?.getIntExtra(IE_CINEMA_ID, 0)
        timeSlotId = intent?.getIntExtra(IE_TIME_SLOT_ID_TIME, 0)
        startTime = intent?.getStringExtra(IE_START_TIME)

    }

    private fun requestData() {
        mMovieBookingModel.getSeatingPlan(
            timeSlotId = timeSlotId,
            bookingDate = movieDate,
            onSuccess = {
                bindData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }


    @SuppressLint("NewApi")
    private fun bindData(seatingPlanVOList: List<SeatingPlanVO>) {

        mSeatingPlanVO = seatingPlanVOList

        //val formatted = LocalDate.parse(movieDate, DateTimeFormatter.ofPattern("EEE, dd MMM"))
        val formattedMovieDate = LocalDate.parse(movieDate)

        Log.d("TAG", "dayOfWeek: ${formattedMovieDate.dayOfWeek}")   //wednesday
        Log.d("TAG", "dayOfMonth: ${formattedMovieDate.dayOfMonth}") //29
        Log.d("TAG", "month: ${formattedMovieDate.month}")   //AUGUST

        tvMovieName.text = movieName
        tvCinimaName.text = cinemaName

        ("${formattedMovieDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())}, " +
                "${formattedMovieDate.dayOfMonth} ${
                    formattedMovieDate.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    )
                }, " +
                "$startTime").also {
            tvMovieDateAndTime.text = it
        }
        mAdapter.setNetworkData(seatingPlanVOList)
    }

    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnBuyTicket.setOnClickListener {

            if (seatSelectedData.isNotEmpty()) {
                startActivity(
                    SnackActivity.newIntent(
                        this,
                        movieId = movieId,
                        movieName = movieName,
                        movieDate = movieDate,
                        cinemaName = cinemaName,
                        cinemaId = cinemaId,
                        timeSlotId = timeSlotId,
                        ticketAmt = ticketAmt,
                        rows = mSelectedRows,
                        seats = mSelectedSeats
                    )
                )
            } else {
                Snackbar.make(window.decorView, "Please select seats!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setUpRecyclerView() {
        rvSeatingPlan.adapter = mAdapter
        rvSeatingPlan.layoutManager = GridLayoutManager(applicationContext, 14)
    }

    override fun onTapSeat(id: Int?, symbol: String?) {
        mSeatingPlanVO?.forEach { seatingPlanVO ->
            if (seatingPlanVO.id == id && seatingPlanVO.isMovieSeatAvailable() && seatingPlanVO.symbol == symbol) {
                if (seatingPlanVO.isSelected) {
                    seatingPlanVO.isSelected = false
                    for (i in seatSelectedData) {
                        if (seatingPlanVO.seatName == i) {
                            totalTicketCount -= 1
                            ticketAmt = ticketAmt?.minus(seatingPlanVO.price ?: 0)
                            seatSelectedData.remove(i)
                        }
                    }
                } else {
                    seatingPlanVO.isSelected = true
                    seatSelectedData.add(seatingPlanVO.seatName ?: "")
                    seatRowSelectedData.add(seatingPlanVO.symbol ?: "")
                    totalTicketCount += 1
                    ticketAmt = ticketAmt?.plus(seatingPlanVO.price ?: 0)
                }
            }
        }

        mSelectedSeats = seatSelectedData.joinToString(",") { it }
        mSelectedRows = seatRowSelectedData.joinToString(",") { it }


        tvSeat.text = seatSelectedData.joinToString(", ") { it }
        tvTicket.text = "$totalTicketCount"
        "Buy Ticket for $ticketAmt$".also { btnBuyTicket.text = it }

        mSeatingPlanVO?.let {
            mAdapter.setNetworkData(it) //update List
        }
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

}