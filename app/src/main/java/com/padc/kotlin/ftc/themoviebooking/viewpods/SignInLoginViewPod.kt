package com.padc.kotlin.ftc.themoviebooking.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginDelegate
import kotlinx.android.synthetic.main.view_pod_sign_in_login.view.*

class SignInLoginViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    lateinit var mDelegate: LoginDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()

        btnConfirm.setOnClickListener {
            mDelegate.onTapConfirm()
        }
    }

    fun setUpMovieListViewPod(delegate: LoginDelegate){
        setDelegate(delegate)
    }

    private fun setDelegate(delegate: LoginDelegate){
        this.mDelegate = delegate
    }
}