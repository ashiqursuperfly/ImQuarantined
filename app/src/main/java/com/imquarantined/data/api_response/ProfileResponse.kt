package com.imquarantined.data.api_response

import com.google.gson.annotations.SerializedName
import com.imquarantined.data.Const
import com.imquarantined.data.UserEntity


/* Created by ashiq.buet16 **/
data class ProfileResponse (
    @SerializedName(Const.Api.Response.SUCCESS)
    var isSuccess: Boolean,
    @SerializedName(Const.Api.Response.MESSAGE)
    var message : String,
    @SerializedName(Const.Api.Response.DATA)
    var data: UserEntity
)
