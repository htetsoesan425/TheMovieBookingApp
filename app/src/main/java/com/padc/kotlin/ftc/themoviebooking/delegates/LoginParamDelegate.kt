package com.padc.kotlin.ftc.themoviebooking.delegates

interface LoginParamDelegate {
    fun onTapLogin(
        email: String?,
        password: String?
    )
}