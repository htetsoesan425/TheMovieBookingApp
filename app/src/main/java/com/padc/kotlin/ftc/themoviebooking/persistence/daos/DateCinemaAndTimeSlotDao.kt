package com.padc.kotlin.ftc.themoviebooking.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.kotlin.ftc.themoviebooking.data.vos.CinemaTimeSlotVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.DateCinemaAndTimeSlotVO

@Dao
interface DateCinemaAndTimeSlotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaTimeSlots(dateCinemaAndTimeSlotVO: DateCinemaAndTimeSlotVO)

    @Query("SELECT * FROM cinema_time_slots")
    fun getAllCinemaTimeSlots(): List<CinemaTimeSlotVO>

    @Query("DELETE FROM cinema_time_slots")
    fun deleteAllCinemaTimeSlots()
}