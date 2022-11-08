package com.padc.kotlin.ftc.themoviebooking

import android.app.Application
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl

class MovieBookingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MovieBookingModelImpl.initDatabase(applicationContext)
    }
}