package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.RegisteredUserVO
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import com.padc.kotlin.ftc.themoviebooking.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_pod_navigation_view.*

class MainActivity : AppCompatActivity(), MovieViewHolderDelegate {

    lateinit var mNowShowingMovieListViewPod: MovieListViewPod
    lateinit var mComingSoonMovieListViewPod: MovieListViewPod
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    //model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )   //not working correctly

        setupDrawer()
        setUpViewPods()
        requestData()
        setUpClickListener()

    }

    private fun setUpClickListener() {

        llLogOut.setOnClickListener {
            mMovieBookingModel.logOut(
                onSuccess = {

                    Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    finish()

                },
                onFailure = {
                    showError(it)
                }
            )
        }
    }

    private fun requestData() {
        //now playing movie
        mMovieBookingModel.getNowPlayingMovies(
            onSuccess = {
                mNowShowingMovieListViewPod.setNewData(it)
            },
            onFailure = {
                showError(it)
            }
        )

        //coming soon movies
        mMovieBookingModel.getComingSoonMovies(
            onSuccess = {
                mComingSoonMovieListViewPod.setNewData(it)
            },
            onFailure = {
                showError(it)
            }
        )

        mMovieBookingModel.getProfile(
            onSuccess = {
                bindData(it)
            },
            onFailure = {
                //showError(it)
            }
        )
    }

    private fun bindData(registeredUserVO: RegisteredUserVO) {


        "Hi ${registeredUserVO.name}!".also { tvName.text = it }

        //home
        /*Glide.with(this)
            .load("$IMAGE_BASE_URL${registeredUserVO.profile_image}")
            .into(ivProfile)*/

        //left menu
        /*Glide.with(this)
            .load("$IMAGE_BASE_URL${registeredUserVO.profile_image}")
            .into(ivLeftMenuProfile)*/
        tvProfileName.text = registeredUserVO.name
        tvGmail.text = registeredUserVO.email
    }


    private fun setupDrawer() {
        setSupportActionBar(toolBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        actionBarDrawerToggle?.let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }
    }

    private fun setUpViewPods() {
        mNowShowingMovieListViewPod = vpNowShowingMovieList as MovieListViewPod
        mNowShowingMovieListViewPod.setUpMovieListViewPod(this, getString(R.string.lbl_now_showing))

        mComingSoonMovieListViewPod = vpComingSoonMovieList as MovieListViewPod
        mComingSoonMovieListViewPod.setUpMovieListViewPod(this, getString(R.string.lbl_coming_soon))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle?.onOptionsItemSelected(item) == true) {
            true
        } else
            return super.onOptionsItemSelected(item)
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}