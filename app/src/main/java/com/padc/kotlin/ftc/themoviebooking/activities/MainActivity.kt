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
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themoviebooking.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieViewHolderDelegate {

    lateinit var mMovieListViewPod: MovieListViewPod
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

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
        )

        setupDrawer()
        setUpViewPods()

        ivDrawerProfile.setOnClickListener {
            Snackbar.make(window.decorView, "Profile picture tapped!", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setupDrawer() {
        setSupportActionBar(toolBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        actionBarDrawerToggle?.let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }

    }

    private fun setUpViewPods() {
        mMovieListViewPod = vpNowShowingMovieList as MovieListViewPod
        mMovieListViewPod.setUpMovieListViewPod(this, "Now Showing")

        mMovieListViewPod = vpComingSoonMovieList as MovieListViewPod
        mMovieListViewPod.setUpMovieListViewPod(this, "Coming Soon")
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

    override fun onTapMovie() {
        startActivity(MovieDetailActivity.newIntent(this));
    }
}