package com.imquarantined.util.api.services

import com.imquarantined.data.Const
import com.imquarantined.data.api_response.AuthResponse
import com.imquarantined.data.api_response.LeaderboardResponse
import com.imquarantined.data.api_response.ProfileResponse
import com.imquarantined.data.api_response.UpdateLocationsResponse
import io.reactivex.Flowable
import org.json.JSONArray
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/* Created by ashiq.buet16 **/

interface CommonApiService {

    @FormUrlEncoded
    @POST(Const.Api.ENDPOINTS.AUTH)
    fun login(
        @Field(Const.Api.Params.POST.ID_TOKEN) idToken: String
    ): Flowable<AuthResponse>


    @FormUrlEncoded
    @POST(Const.Api.ENDPOINTS.UPDATE_LOCATION)
    fun updateLocations(
        @Field(Const.Api.Params.POST.LOCATION_ARRAY) locations: JSONArray
    ): Flowable<UpdateLocationsResponse>

    @GET(Const.Api.ENDPOINTS.GET_PROFILE)
    fun getProfile(): Flowable<ProfileResponse>

    @GET(Const.Api.ENDPOINTS.GET_LEADERBOARD)
    fun getLeaderBoard(): Flowable<LeaderboardResponse>


    /*

        @GET(Const.Api.ENDPOINTS.GET_PACKING_ORDER_LIST)
        fun getPackingList(@Query(Const.Api.Params.GET.LIMIT) limit: Int): Flowable<AllOrdersResponse>

        @GET
        fun getOrderDetails(@Url url:String): Flowable<OrderDetailsResponse>
    */

//    @FormUrlEncoded
//    @POST(Const.Api.ENDPOINTS.LIST_VALIDATION)
//    fun validateList(
//        @Field(Const.Api.Params.POST.OPERATION_TYPE) operationType: String,
//        @Field(Const.Api.Params.POST.ID) orderId: String,
//        @Field(Const.Api.Params.POST.PRODUCTS_ARRAY) products: JSONArray
//    ): Flowable<PackingValidationResponse>
//
//    @FormUrlEncoded
//    @POST(Const.Api.ENDPOINTS.CHANGE_ORDER_STATE)
//    fun changeState(
//        @Field(Const.Api.Params.POST.OPERATION_TYPE) operationType: String,
//        @Field(Const.Api.Params.POST.ID) id: String
//    ): Flowable<SimpleResponse>
//
//    @FormUrlEncoded
//    @POST(Const.Api.ENDPOINTS.ISSUE_IN_PACK)
//    fun issueInPack(@Field(Const.Api.Params.POST.ID) orderId: String): Flowable<SimpleResponse>

}