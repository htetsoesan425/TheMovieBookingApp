package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieSeatAdapter
import com.padc.kotlin.ftc.themoviebooking.dummy.DUMMY_SEATS
import kotlinx.android.synthetic.main.activity_seating_plan.*

class SeatingPlanActivity : AppCompatActivity() {

    private val mAdapter: MovieSeatAdapter = MovieSeatAdapter()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SeatingPlanActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seating_plan)

        setUpRecyclerView()
        setUpListener()
    }

    private fun setUpListener() {
        btnBuyTicket.setOnClickListener {
            startActivity(SnackActivity.newIntent(this))
        }
    }

    private fun setUpRecyclerView() {
        rvSeatingPlan.adapter = mAdapter
        rvSeatingPlan.layoutManager = GridLayoutManager(applicationContext, 10)
        mAdapter.setNewData(DUMMY_SEATS)
    }
}