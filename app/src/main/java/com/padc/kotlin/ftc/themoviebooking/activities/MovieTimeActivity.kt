package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.DateAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieTimeListAdapter
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.TimeSlotDateVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapMovieTimeDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.TimeSlotDelegate
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_movie_time.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieTimeActivity : AppCompatActivity(), TimeSlotDelegate, OnTapMovieTimeDelegate {

    private var mCinemaId: Int? = 0
    private var mCinemaName: String? = ""
    private var mCinemaTimeSlotVOList: List<CinemaTimeSlotVO> = listOf()
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    private var mTimeSlotId: Int? = 0
    private var mStartTime: String? = ""
    private var mSelectedPosition: Int = -1
    private var mCurrentDate: String? = ""
    private var mDateList: MutableList<TimeSlotDateVO> = mutableListOf()
    private lateinit var mMovieTimeListAdapter: MovieTimeListAdapter
    private lateinit var mDateAdapter: DateAdapter
    private var movieId: Int? = 0
    private var movieName: String? = ""

    companion object {

        private const val IE_MOVIE_ID = "IE_MOVIE_ID"
        private const val IE_MOVIE_NAME = "IE_MOVIE_NAME"

        fun newIntent(context: Context, movieId: Int?, movieName: String): Intent {
            val intent = Intent(context, MovieTimeActivity::class.java)
            intent.putExtra(IE_MOVIE_ID, movieId)
            intent.putExtra(IE_MOVIE_NAME, movieName)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_time)
        setUpRecyclerView()

        movieId = intent?.getIntExtra(IE_MOVIE_ID, 0)
        movieName = intent?.getStringExtra(IE_MOVIE_NAME)

        generateDatesForTwoWeeks()
        setUpListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateDatesForTwoWeeks() {
        var currentDate = LocalDate.now()
        var currentDay = getDayAndDayName(currentDate).first
        var dayOfWeekTwoChar = getDayAndDayName(currentDate).second

        mDateList.add(TimeSlotDateVO(currentDate.toString(), currentDay, dayOfWeekTwoChar))

        for (i in 0..12) {
            currentDate = currentDate.plusDays(1)
            currentDay = getDayAndDayName(currentDate).first
            dayOfWeekTwoChar = getDayAndDayName(currentDate).second
            mDateList.add(TimeSlotDateVO(currentDate.toString(), currentDay, dayOfWeekTwoChar))
        }

        mDateList[0].isSelected = true
        mCurrentDate = mDateList[0].date
        mDateAdapter.setNewData(mDateList)

        Log.d("TAG", "generateDatesForTwoWeeks: $currentDate")
        Log.d("TAG", "generateDatesForTwoWeeks: $currentDay")
        Log.d("TAG", "generateDatesForTwoWeeks: $dayOfWeekTwoChar")

        requestData(mDateList[0], movieId)  //initial call
    }

    private fun requestData(timeSlotDateVO: TimeSlotDateVO, movieId: Int?) {
        mMovieBookingModel.getCinemaTimeSlot(
            movieId = movieId.toString(),
            timeSlotDateVO.date,
            onSuccess = {
                bindData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun bindData(cinemaTimeSlotVOList: List<CinemaTimeSlotVO>) {
        mCinemaTimeSlotVOList = cinemaTimeSlotVOList
        mMovieTimeListAdapter.setNewData(cinemaTimeSlotVOList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDayAndDayName(currentDate: LocalDate): Pair<String, String> {
        val currentDay = currentDate.format(DateTimeFormatter.ofPattern("dd"))
        val currentDayName = currentDate.format(DateTimeFormatter.ofPattern("EEEE"))
        val twoCharDayName = currentDayName.substring(0, 2)
        return Pair(currentDay, twoCharDayName)
    }

    private fun setUpRecyclerView() {
        mDateAdapter = DateAdapter(this, mSelectedPosition)
        rvDate.adapter = mDateAdapter
        rvDate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mMovieTimeListAdapter = MovieTimeListAdapter(this)
        rvPlaceTime.adapter = mMovieTimeListAdapter
        rvPlaceTime.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpListener() {
        btnNext.setOnClickListener {
            if (mTimeSlotId != 0) {
                startActivity(
                    SeatingPlanActivity.newIntent(
                        this,
                        movieId,
                        movieName,
                        mCurrentDate,
                        mCinemaName,
                        mCinemaId,
                        mTimeSlotId,
                        mStartTime
                    )
                )
            } else {
                showError(getString(R.string.lbl_please_choose_movie_time))
            }
        }

        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onTapDate(date: String, selectedPosition: Int) {
        mSelectedPosition = selectedPosition
        mDateList.forEach { dateVO ->
            dateVO.isSelected = dateVO.date == date
            if (dateVO.date == date) {
                //dateVO.isSelected = true
                mCurrentDate = dateVO.date
                requestData(dateVO, movieId = movieId)
            }
        }
        mDateAdapter.setNewData(mDateList)
    }

    override fun onTapMovieTime(
        timeslotId: Int?,
        startTime: String?,
        cinemaName: String?,
        cinemaId: Int?
    ) {
        mTimeSlotId = timeslotId
        mStartTime = startTime
        mCinemaName = cinemaName
        mCinemaId = cinemaId
        mCinemaTimeSlotVOList.forEach {
            it.timeslots?.forEach { timeSlotVO ->
                timeSlotVO.isSelected = timeSlotVO.cinemaDayTimeslotId == timeslotId
            }
        }
        mMovieTimeListAdapter.setNewData(mCinemaTimeSlotVOList)
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }
}