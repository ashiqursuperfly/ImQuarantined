package com.stylinecollection.opflex.util.api

import com.imquarantined.data.Const
import com.imquarantined.util.api.services.CommonApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    object OkHttpInterceptor {

        val interceptor = Interceptor { chain ->

            val request = chain.request().newBuilder()

//            val token = PrefUtil.get(Const.PrefProp.TOKEN,"-1")

//            if(token != "-1"){
//                request.addHeader(Const.Api.Params.HEADER.AUTHORIZATION, "Bearer $token")
//            }

            chain.proceed(request.build())
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(OkHttpInterceptor.interceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(Const.Api.BASE_URL)
        .build()

    fun cancelAllRequest() {
        client.dispatcher().cancelAll()
    }

    fun createCommonApiService(): CommonApiService {
        return retrofit.create(CommonApiService::class.java)
    }

}