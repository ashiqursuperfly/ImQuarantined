package com.imquarantined.data

import com.google.gson.annotations.SerializedName

/* Created by ashiq.buet16 **/

data class UserEntity (
    var position: Int = -1,
    @SerializedName(Const.Api.Response.USER_NAME)
    var userName: String,
    @SerializedName(Const.Api.Response.PHOTO_URL)
    var imageUrl: String,
    @SerializedName(Const.Api.Response.TOTAL_POINTS)
    var points: Int,
    @SerializedName(Const.Api.Response.CUR_STREAK)
    var currentStreak: Int,
    @SerializedName(Const.Api.Response.DAYS_QUARANTINED)
    var daysQuarantined: Int,
    @SerializedName(Const.Api.Response.HIGHEST_STREAK)
    var highestStreak: Int
)