package com.padc.kotlin.ftc.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.adapters.ViewPagerEntryTypeListAdapter
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginDelegate
import com.padc.kotlin.ftc.themoviebooking.dummy.dummyEntryTypeList
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity() : AppCompatActivity(), LoginDelegate {

    private lateinit var mViewPagerEntryTypeListAdapter: ViewPagerEntryTypeListAdapter

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
        mViewPagerEntryTypeListAdapter = ViewPagerEntryTypeListAdapter(this, this)
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

    override fun onTapConfirm() {
        startActivity(MainActivity.newIntent(this))
    }


}