package com.padc.kotlin.ftc.themoviebooking.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movies: MovieVO?)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieVO>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): MovieVO?

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getAllMoviesByType(type: String): List<MovieVO>

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}