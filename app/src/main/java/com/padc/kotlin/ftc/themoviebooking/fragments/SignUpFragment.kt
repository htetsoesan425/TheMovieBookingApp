package com.padc.kotlin.ftc.themoviebooking.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.UserVO
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapConfirmDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.RegisterParamDelegate
import com.padc.kotlin.ftc.themoviebooking.viewpods.SignInLoginViewPod
import kotlinx.android.synthetic.main.fragment_login.vpLoginButtons
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(), OnTapConfirmDelegate {

    private lateinit var mSignInLoginTypeEntryViewPod: SignInLoginViewPod
    private lateinit var userVO: UserVO
    private var mDelegate: OnTapConfirmDelegate? = null
    private var mRegisterParamDelegate: RegisterParamDelegate? = null
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null
    private var phone: String? = null
    private var errorMsg: String? = "error"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPods()
        super.onViewCreated(view, savedInstanceState)
        //setUpClickListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mRegisterParamDelegate = context as RegisterParamDelegate
    }

    private fun callConfirmFunc(name: String?, email: String?, password: String?, phone: String?) {
        mRegisterParamDelegate?.onTapRegister(name, email, password, phone)
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
        Log.d("TAG", "checkValidation:$tilName.toString()")
        if (edtName == null || edtName.toString().isEmpty()) {
            tilName.error = "Please fill user name!"
            errorMsg += "error"
        } else {
            tilName.error = null
            name = edtName.text.toString()
            errorMsg = ""
        }
        if (edtMail == null || edtMail.toString().isEmpty()) {
            tilName.error = "Please fill user email!"
            errorMsg += "error"
        } else {
            tilName.error = null
            email = edtMail.text.toString()
            errorMsg = ""
        }
        if (edtPhone == null || edtPhone.toString().isEmpty()) {
            tilPhone.error = "Please fill phone number!"
            errorMsg += "error"
        } else {
            phone = edtPhone.text.toString()
            tilPhone.error = null
            errorMsg = ""
        }
        if (edtPassword == null || edtPassword.toString().isEmpty()) {
            tilPassword.error = "Please fill password!"
            errorMsg += "error"
        } else {
            password = edtPassword.text.toString()
            tilPassword.error = null
            errorMsg = ""
        }

        if (errorMsg?.isEmpty() == true) {
            callConfirmFunc(name, email, password, phone)
        }
    }
}