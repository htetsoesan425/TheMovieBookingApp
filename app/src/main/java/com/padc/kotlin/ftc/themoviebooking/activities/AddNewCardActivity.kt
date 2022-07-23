package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.padc.kotlin.ftc.themoviebooking.R
import kotlinx.android.synthetic.main.activity_add_new_card.*

class AddNewCardActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCardActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        btnAdd.setOnClickListener {
            startActivity(PaymentDetailActivity.newIntent(this))
        }

        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }


}