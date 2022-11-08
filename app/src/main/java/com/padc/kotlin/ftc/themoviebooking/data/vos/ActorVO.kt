package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "actors")
data class ActorVO(

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @SerializedName("gender")
    @ColumnInfo(name = "gender")
    val gender: Int?,

    @SerializedName("id")
    @PrimaryKey
    val id: Int?,

    @SerializedName("known_for")
    @ColumnInfo(name = "adult")
    val known_for: List<MovieVO>?,

    @SerializedName("known_for_department")
    @ColumnInfo(name = "adult")
    val knownForDepartment: String?,

    @SerializedName("name")
    @ColumnInfo(name = "adult")
    val name: String?,

    @SerializedName("popularity")
    @ColumnInfo(name = "adult")
    val popularity: Double?,

    @SerializedName("profile_path")
    @ColumnInfo(name = "adult")
    val profilePath: String?,

    @SerializedName("original_name")
    @ColumnInfo(name = "adult")
    val originalName: String?,

    @SerializedName("cast_id")
    @ColumnInfo(name = "adult")
    val castId: Int?,

    @SerializedName("credit_id")
    @ColumnInfo(name = "adult")
    val creditId: String?,

    @SerializedName("character")
    @ColumnInfo(name = "adult")
    val character: String?,

    @ColumnInfo(name = "adult")
    @SerializedName("order")
    val order: Int?,
)