package com.padc.kotlin.ftc.themoviebooking.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginParamDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapConfirmDelegate
import com.padc.kotlin.ftc.themoviebooking.viewpods.SignInLoginViewPod
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), OnTapConfirmDelegate {

    private var errorMsg: String? = "error"
    private lateinit var mSignInLoginTypeEntryViewPod: SignInLoginViewPod
    var mDelegate: OnTapConfirmDelegate? = null
    var mLoginParamDelegate: LoginParamDelegate? = null
    private var email: String? = null
    private var password: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //mDelegate = context as LoginDelegate
        mLoginParamDelegate = context as LoginParamDelegate
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPods()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpViewPods() {
        mSignInLoginTypeEntryViewPod = vpLoginButtons as SignInLoginViewPod
        mDelegate?.let {
            mSignInLoginTypeEntryViewPod.setUpMovieListViewPod(it)
        }
        mSignInLoginTypeEntryViewPod.setDelegate(this)
    }

    override fun onTapConfirm() {
        checkValidation()
    }

    private fun checkValidation() {
        if (edtLoginEmail == null || edtLoginEmail.toString().isEmpty()) {
            tilLoginEmail.error = "Please fill email!"
            errorMsg += "error"
        } else {
            tilLoginEmail.error = null
            email = edtLoginEmail.text.toString()
            errorMsg = ""
        }
        if (edtLoginPassword == null || edtLoginPassword.toString().isEmpty()) {
            tilLoginPassword.error = "Please fill password!"
            errorMsg += "error"
        } else {
            password = edtLoginPassword.text.toString()
            tilLoginPassword.error = null
            errorMsg = ""
        }

        if (errorMsg?.isEmpty() == true) {
            callConfirmFunc(email, password)
        }
    }

    private fun callConfirmFunc(email: String?, password: String?) {
        mLoginParamDelegate?.onTapLogin(email = email, password = password)
    }
}