package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.ViewPagerEntryTypeListAdapter
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginParamDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.RegisterParamDelegate
import com.padc.kotlin.ftc.themoviebooking.dummy.dummyEntryTypeList
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModel
import com.padc.kotlin.ftc.themoviebooking.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), RegisterParamDelegate, LoginParamDelegate {

    private lateinit var mViewPagerEntryTypeListAdapter: ViewPagerEntryTypeListAdapter

    //model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpEntryTypeListTab()
        setUpEntryTypeListViewPager()
        attachTabWithViewPager()
        clickListeners()

    }

    private fun clickListeners() {
    }

    private fun attachTabWithViewPager() {
        TabLayoutMediator(tabEntryStyle, viewPagerEntryTypeList) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.lbl_login)
                else -> tab.text = getString(R.string.lbl_sign_in)
            }
        }.attach()
    }

    private fun setUpEntryTypeListViewPager() {
        mViewPagerEntryTypeListAdapter = ViewPagerEntryTypeListAdapter(this)
        viewPagerEntryTypeList.adapter = mViewPagerEntryTypeListAdapter
    }

    private fun setUpEntryTypeListTab() {
        dummyEntryTypeList.forEach {
            tabEntryStyle.newTab().apply {
                text = it
                tabEntryStyle.addTab(this)
            }
        }
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    override fun onTapRegister(name: String?, email: String?, password: String?, phone: String?) {
        mMovieBookingModel.registerWithEmail(
            name = name,
            email = email,
            password = password,
            phone = phone,
            onSuccess = {
                if (it) {
                    startActivity(MainActivity.newIntent(this))
                    finish()
                } else {
                    showError("getCreditByMovie: $it")
                }
            },
            onFailure = {
                showError("getCreditByMovie: $it")
            }
        )
    }

    override fun onTapLogin(email: String?, password: String?) {
        mMovieBookingModel.loginWithEmail(
            email = email,
            password = password,
            onSuccess = {
                Log.d("TAG", "onTapLogin: $it")
                if (it) {
                    startActivity(MainActivity.newIntent(this))
                    finish()
                } else {
                    showError("getCreditByMovie: $it")
                }
            },
            onFailure = {
                showError("getCreditByMovie: $it")
            }
        )
    }

}