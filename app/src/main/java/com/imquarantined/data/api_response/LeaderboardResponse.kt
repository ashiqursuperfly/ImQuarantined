package com.imquarantined.data.api_response

import com.google.gson.annotations.SerializedName
import com.imquarantined.data.Const
import com.imquarantined.data.UserEntity

/* Created by ashiq.buet16 **/

data class LeaderboardResponse(
    @SerializedName(Const.Api.Response.SUCCESS)
    var isSuccess: Boolean,
    @SerializedName(Const.Api.Response.MESSAGE)
    var message : String,
    @SerializedName(Const.Api.Response.DATA)
    var data: LeaderboardResponseData
)

data class LeaderboardResponseData (
    @SerializedName(Const.Api.Response.TOP_USERS)
    var topUsers : Array<UserEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LeaderboardResponseData

        if (!topUsers.contentEquals(other.topUsers)) return false

        return true
    }

    override fun hashCode(): Int {
        return topUsers.contentHashCode()
    }
}