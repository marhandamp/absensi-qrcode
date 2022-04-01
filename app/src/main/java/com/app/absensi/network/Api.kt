package com.app.absensi.network

import com.app.absensi.data.request.LoginRequest
import com.app.absensi.data.model.ModelLogin
import com.app.absensi.data.response.LogoutResponse
import com.app.absensi.data.model.ModelDataMatakuliah
import com.app.absensi.data.model.ModelDataRelasi
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("relasiModel")
    fun getRelasi(): Call<ModelDataRelasi>

    @POST("login")
    fun postLogin(
        @Body req: LoginRequest
    ): Call<ModelLogin>

    @POST("logout")
    fun postLogout(): Call<LogoutResponse>

    @GET("mata-kuliah")
    fun getMatakuliah(): Call<ModelDataMatakuliah>
}