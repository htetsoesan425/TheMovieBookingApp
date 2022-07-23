package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.ComboSetAdapter
import com.padc.kotlin.ftc.themoviebooking.adapters.PaymentMethodAdapter
import kotlinx.android.synthetic.main.activity_movie_time.btnBack
import kotlinx.android.synthetic.main.activity_snack.*

class SnackActivity : AppCompatActivity() {

    private lateinit var mComboSetAdapter: ComboSetAdapter
    private lateinit var mPaymentMethodAdapter: PaymentMethodAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SnackActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)

        setUpRecyclerView()
        setUpListener()
    }

    private fun setUpRecyclerView() {
        mComboSetAdapter = ComboSetAdapter()
        rvComboSet.adapter = mComboSetAdapter
        rvComboSet.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mPaymentMethodAdapter = PaymentMethodAdapter()
        rvPaymentMethod.adapter = mPaymentMethodAdapter
        rvPaymentMethod.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnPay.setOnClickListener {
            startActivity(PaymentDetailActivity.newIntent(this))
        }
    }
}