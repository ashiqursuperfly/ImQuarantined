package com.imquarantined.data.api_response

import com.google.gson.annotations.SerializedName
import com.imquarantined.data.Const

/* Created by ashiq.buet16 **/
data class HomeResponse (
    @SerializedName(Const.Api.Response.SUCCESS)
    var isSuccess: Boolean,
    @SerializedName(Const.Api.Response.MESSAGE)
    var message : String,
    @SerializedName(Const.Api.Response.DATA)
    var data: HomeResponseData
)

data class HomeResponseData (
    @SerializedName(Const.Api.Response.PROGRESS)
    var progress: Int,
    @SerializedName(Const.Api.Response.HOUR)
    var hr: Int,
    @SerializedName(Const.Api.Response.MINUTE)
    var min: Int,
    @SerializedName(Const.Api.Response.SECOND)
    var sec: Int,
    @SerializedName(Const.Api.Response.CUR_STREAK)
    var currentStreak : Int
)