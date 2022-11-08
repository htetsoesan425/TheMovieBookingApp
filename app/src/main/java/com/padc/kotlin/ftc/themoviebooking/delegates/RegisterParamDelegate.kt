package com.padc.kotlin.ftc.themoviebooking.delegates

interface RegisterParamDelegate {
    fun onTapRegister(
        name: String?,
        email: String?,
        password: String?,
        phone: String?
    )
}