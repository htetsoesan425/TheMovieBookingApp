package com.padc.kotlin.ftc.themoviebooking.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.kotlin.ftc.themoviebooking.data.vos.*
import com.padc.kotlin.ftc.themoviebooking.persistence.daos.*

@Database(
    entities = [RegisteredUserVO::class, MovieVO::class, SnackVO::class, PaymentVO::class, DateCinemaAndTimeSlotVO::class],
    version = 2,
    exportSchema = false
)   //declare entities
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "THE_MOVIE_BOOKING_DB"

        var dbInstance: MovieDatabase? = null

        fun getDBInstance(context: Context): MovieDatabase? {
            when (dbInstance) {
                null -> dbInstance =
                    Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return dbInstance
        }
    }

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao   //declare Dao
    abstract fun dateCinemaAndTimeSlotDao(): DateCinemaAndTimeSlotDao
    abstract fun paymentDao(): PaymentDao
    abstract fun snackDao(): SnackDao   //declare Dao
}