package com.wisnu.salttechnicaltest.utils

import com.google.gson.annotations.SerializedName

abstract class BaseResponse{
    @SerializedName("status")
    val status: String? = null
    @SerializedName("code")
    val code: String? = null
    @SerializedName("message")
    val message: String? = null
}