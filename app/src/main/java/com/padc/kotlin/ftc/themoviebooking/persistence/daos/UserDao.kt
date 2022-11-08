package com.padc.kotlin.ftc.themoviebooking.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.kotlin.ftc.themoviebooking.data.vos.RegisteredUserVO

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userVO: RegisteredUserVO)

    @Query("SELECT token From users")
    fun getToken(): String

    @Query("UPDATE users SET token = null")
    fun deleteToken()

}
