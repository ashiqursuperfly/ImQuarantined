package com.imquarantined.data.api_response

import com.google.gson.annotations.SerializedName
import com.imquarantined.data.Const

/* Created by ashiq.buet16 **/

data class UpdateLocationsResponse(
    @SerializedName(Const.Api.Response.SUCCESS)
    var isSuccess: Boolean,
    @SerializedName(Const.Api.Response.MESSAGE)
    var message : String,
    @SerializedName(Const.Api.Response.DATA)
    var data: LocationResponseData
)

data class LocationResponseData (
    @SerializedName(Const.Api.Response.FAILED_AT)
    var failedAt : String
)