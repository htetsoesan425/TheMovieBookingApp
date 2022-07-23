package com.padc.kotlin.ftc.themoviebooking.models

import alirezat775.lib.carouselview.CarouselModel

class SampleModel constructor(private var expireDate: String) : CarouselModel() {

    fun getId(): String {
        return expireDate
    }
}
