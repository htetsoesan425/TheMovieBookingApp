package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.CheckoutVO
import com.padc.kotlin.ftc.themoviebooking.data.vos.MovieVO
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import com.padc.kotlin.ftc.themoviebooking.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_voucher.*
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class VoucherActivity : AppCompatActivity() {

    private var mCinemaName: String? = ""
    private var checkOutVO: CheckoutVO? = null

    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {

        private const val IE_CINEMA_NAME = "IE_CINEMA_NAME"
        private const val IE_CHECKOUT_VO = "IE_CHECKOUT_VO"

        fun newIntent(
            context: Context,
            cinemaName: String?,
            checkoutVOString: String
        ): Intent {

            val intent = Intent(context, VoucherActivity::class.java)
            intent.putExtra(IE_CINEMA_NAME, cinemaName)
            intent.putExtra(IE_CHECKOUT_VO, checkoutVOString)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher)

        getIntentExtraValues()
        bindData(checkOutVO)
        setupClickListener()

    }

    private fun setupClickListener() {
        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(checkOutVO: CheckoutVO?) {

        requestData(checkOutVO)

        tvBookingNumber.text = checkOutVO?.bookingNo ?: ""

        val formattedBookingDate = LocalDate.parse(checkOutVO?.bookingDate)
        val bookingDate =
            "${checkOutVO?.timeslot?.startTime} - ${formattedBookingDate.dayOfMonth} ${
                formattedBookingDate.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                )
            }"

        tvShowTimeDate.text = bookingDate
        tvTheater.text = mCinemaName
        tvRow.text = checkOutVO?.row ?: ""
        tvSeat.text = checkOutVO?.seat ?: ""
        tvPrice.text = checkOutVO?.total ?: ""
        checkOutVO?.qrCode?.let { barcodeGenerator(it) }

    }

    private fun requestData(checkOutVO: CheckoutVO?) {
        mMovieBookingModel.getMovieDetail(
            movieId = checkOutVO?.movieId.toString(),
            onSuccess = {
                bindMovieData(it)
            },
            onFailure = {
                showError(it)
            })
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    private fun bindMovieData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMoviePoster)
        tvMovieName.text = movie.title
        "${movie.runtime} - IMAX".also { tvMovieDuration.text = it }
    }

    private fun getIntentExtraValues() {
        mCinemaName = intent?.getStringExtra(IE_CINEMA_NAME)
        checkOutVO = Gson().fromJson(intent?.getStringExtra(IE_CHECKOUT_VO), CheckoutVO::class.java)
    }

    private fun barcodeGenerator(barCodeStr: String) {
        try {

            //val tempUrl = "uploads/img/qr-code/img-MegaTV-6263-9764.png"

            val hintMap: Hashtable<EncodeHintType, ErrorCorrectionLevel> =
                Hashtable<EncodeHintType, ErrorCorrectionLevel>()
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
            val codeWriter: Writer
            codeWriter = Code128Writer()

            /*val byteMatrix: BitMatrix =
                codeWriter.encode(tempUrl, BarcodeFormat.CODE_128, 400, 200, hintMap)  */

            val byteMatrix: BitMatrix =
                codeWriter.encode(barCodeStr, BarcodeFormat.CODE_128, 400, 200, hintMap)
            val width = byteMatrix.width
            val height = byteMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            for (i in 0 until width) {
                for (j in 0 until height) {
                    bitmap.setPixel(i, j, if (byteMatrix[i, j]) Color.BLACK else Color.WHITE)
                }
            }
            ivBarcode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Snackbar.make(window.decorView, e.message ?: " ", Snackbar.LENGTH_SHORT).show()
        }

    }
}