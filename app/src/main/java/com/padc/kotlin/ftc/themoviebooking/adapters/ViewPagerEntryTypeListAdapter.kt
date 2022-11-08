package com.padc.kotlin.ftc.themoviebooking.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.padc.kotlin.ftc.themoviebooking.fragments.LoginFragment
import com.padc.kotlin.ftc.themoviebooking.fragments.SignUpFragment

class ViewPagerEntryTypeListAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginFragment()
            }
            else -> {
                SignUpFragment()
            }
        }
    }

}