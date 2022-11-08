package com.padc.kotlin.ftc.themoviebooking.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.padc.kotlin.ftc.themoviebooking.delegates.OnTapConfirmDelegate
import kotlinx.android.synthetic.main.view_pod_sign_in_login.view.*

class SignInLoginViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    lateinit var mDelegate: OnTapConfirmDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()

        btnConfirm.setOnClickListener {
            mDelegate.onTapConfirm()
        }
    }

    fun setUpMovieListViewPod(delegate: OnTapConfirmDelegate) {
        setDelegate(delegate)
    }

    fun setDelegate(delegate: OnTapConfirmDelegate) {
        this.mDelegate = delegate
    }
}