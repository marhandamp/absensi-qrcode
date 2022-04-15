package com.app.absensi.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.smartinovasi.kopiku.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private val BASE_URL = "https://irwandi.my.id/"

//    val instance: Api by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okhttpClient()) // Add our Okhttp client
//            .build()
//        retrofit.create(Api::class.java)
//    }

    fun instance(context: Context): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(context))
            .build()

        return retrofit.create(Api::class.java)
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    fun getBaseUrl(): String{
        return BASE_URL
    }

}