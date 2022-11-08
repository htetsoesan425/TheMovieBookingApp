package com.padc.kotlin.ftc.themoviebooking.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.kotlin.ftc.themoviebooking.data.vos.PaymentVO

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayments(movies: List<PaymentVO>)

    @Query("SELECT * FROM payments")
    fun getAllPayments(): List<PaymentVO>

    @Query("DELETE FROM payments")
    fun deleteAllPayments()

}