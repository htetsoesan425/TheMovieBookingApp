package com.padc.kotlin.ftc.themoviebooking.activities

import CardCarouselAdapter
import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.models.SampleModel
import kotlinx.android.synthetic.main.activity_payment_detail.*

class PaymentDetailActivity : AppCompatActivity() {

    lateinit var mCardCarouselAdapter: CardCarouselAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PaymentDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)

        setUpCarouselView()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        btnConfirm.setOnClickListener {
            startActivity(VoucherActivity.newIntent(this))
        }
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
        btnAddNewCard.setOnClickListener {
            startActivity(AddNewCardActivity.newIntent(this))
        }

    }

    private fun setUpCarouselView() {
        mCardCarouselAdapter = CardCarouselAdapter()
        val carousel = Carousel(this, carouselView, mCardCarouselAdapter)
        carousel.setOrientation(CarouselView.HORIZONTAL, false)
        carousel.autoScroll(true, 5000, true)
        carousel.scaleView(true)

        carousel.add(SampleModel("08/21"))
        carousel.add(SampleModel("08/22"))
        carousel.add(SampleModel("08/23"))
        carousel.add(SampleModel("08/24"))
        carousel.add(SampleModel("08/25"))
        carousel.add(SampleModel("08/26"))
        carousel.add(SampleModel("08/27"))
        carousel.add(SampleModel("08/28"))
        carousel.add(SampleModel("08/29"))
        carousel.add(SampleModel("08/30"))
    }
}
