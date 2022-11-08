package com.padc.kotlin.ftc.themoviebooking.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters.GenreIdsTypeConverter
import com.padc.kotlin.ftc.themoviebooking.persistence.typeconverters.GenreListTypeConverter

@Entity(tableName = "movies")
@TypeConverters(
    GenreListTypeConverter::class,
    GenreIdsTypeConverter::class
)

data class MovieVO(

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("id")
    @PrimaryKey
    val id: Int = 0,

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String?,

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,


    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String?,

    @SerializedName("video")
    @ColumnInfo(name = "video")
    val video: Boolean?,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int = 0,

    @SerializedName("budget")
    @ColumnInfo(name = "budget")
    val budget: Int?,

    @SerializedName("genres")
    @ColumnInfo(name = "genres")
    val genres: List<GenreVO>?,

    @SerializedName("homepage")
    @ColumnInfo(name = "homepage")
    val homepage: String?,

    @SerializedName("imdb_id")
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
    /* @SerializedName("production_companies")
     val productionCompanies: List<ProductionCompanyVO>?,
     @SerializedName("production_countries")
     val productionCountries: List<ProductionCountryVO>?,*/

    @SerializedName("revenue")
    @ColumnInfo(name = "revenue")
    val revenue: Int?,

    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    val runtime: Int?,

/*    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageVO>?,*/
    @SerializedName("status")
    @ColumnInfo(name = "status")
    val status: String?,

    @SerializedName("tagline")
    @ColumnInfo(name = "tagline")
    val tagline: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    ) {
    fun getRatingBasedOnFiveStar(): Float {
        return voteAverage?.div(2)?.toFloat() ?: 0.0f
    }

/*    fun getGenresAsCommaSeparatedString(): String {
        return genres?.map { it.name }?.joinToString(", ") ?: ""
    }

    fun getProductionCountriesCommaSeparatedString(): String {
        return productionCountries?.map { it.name }?.joinToString(", ") ?: ""
    }*/
}

const val NOW_SHOWING = "NOW_SHOWING"
const val COMING_SOON = "COMING_SOON"