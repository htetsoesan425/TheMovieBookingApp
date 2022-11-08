package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        isNewUser()

        btnGetStarted.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
    }

    private fun isNewUser() {
        mMovieBookingModel.getProfile(
            onSuccess = { registeredUserVO ->
                registeredUserVO.token?.let {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            },
            onFailure = {

            }
        )

    }
}