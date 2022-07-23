package com.padc.kotlin.ftc.themoviebooking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginDelegate
import com.padc.kotlin.ftc.themoviebooking.viewpods.SignInLoginViewPod
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment(private val mDelegate: LoginDelegate) : Fragment() {

    private lateinit var mSignInLoginTypeEntryViewPod: SignInLoginViewPod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPods()
    }

    private fun setUpViewPods() {
        mSignInLoginTypeEntryViewPod = vpLoginButtons as SignInLoginViewPod
        mSignInLoginTypeEntryViewPod.setUpMovieListViewPod(mDelegate)
    }
}