package com.imquarantined.data.api_response

import com.google.gson.annotations.SerializedName
import com.imquarantined.data.Const

/* Created by ashiq.buet16 **/

data class AuthResponse (
    @SerializedName(Const.Api.Response.SUCCESS)
    var isSuccess: Boolean,
    @SerializedName(Const.Api.Response.MESSAGE)
    var message : String,
    @SerializedName(Const.Api.Response.DATA)
    var data: AuthResponseData
)

data class AuthResponseData (
    @SerializedName(Const.Api.Response.USER_NAME)
    var userName: String
)
