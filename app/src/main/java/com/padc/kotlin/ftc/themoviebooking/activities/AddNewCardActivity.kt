package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_add_new_card.*


class AddNewCardActivity : AppCompatActivity() {

    //model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

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

            mMovieBookingModel.createCard(
                cardNumber = edtCardNumber.text.toString(),
                cardHolder = edtCardHolder.text.toString(),
                expirationDate = edtExpirationDate.text.toString(),
                cvc = edtCVC.text.toString().toInt(),
                onSuccess = {

                    resultSetAndBackPressed()

                    /*setResult(RESULT_OK, intent)
                    finish()*/

                    //        resultSetAndBackPressed()
                    //startActivity(PaymentDetailActivity.newIntent(this, 10))
                },
                onFailure = {
                    showError(it)
                }
            )

        }

        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun resultSetAndBackPressed() {
        /*val returnIntent = newIntent(this)
        returnIntent.putExtra("result", result)*/
        setResult(RESULT_OK, intent)
        finish()

    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

}