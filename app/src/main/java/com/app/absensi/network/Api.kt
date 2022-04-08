package com.app.absensi.network

import com.app.absensi.data.model.*
import com.app.absensi.data.request.AbsensiRequest
import com.app.absensi.data.request.LoginAdminRequest
import com.app.absensi.data.request.LoginDosenRequest
import com.app.absensi.data.response.LogoutAdminResponse
import com.app.absensi.data.response.LogoutDosenResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("api/relasiModel")
    fun getRelasi(): Call<ModelDataRelasi>

    @POST("api/login")
    fun postLoginDosen(
        @Body req: LoginDosenRequest
    ): Call<ModelLoginDosen>

    @POST("api/logout")
    fun postLogoutDosen(): Call<LogoutDosenResponse>

    @POST("api/loginAdmin")
    fun postLoginAdmin(
        @Body req: LoginAdminRequest
    ): Call<ModelLoginAdmin>

    @POST("api/logoutAdmin")
    fun postLogoutAdmin(): Call<LogoutAdminResponse>


    @GET("api/mata-kuliah")
    fun getMatakuliah(): Call<ModelDataMatakuliah>

    @Multipart
    @POST("api/qrcode")
    fun postQRCode(@Part image: MultipartBody.Part): Call<ModelDataQRCode>

    @POST("api/absensi")
    fun postAbsensi(
        @Body req: AbsensiRequest
    ): Call<ModelDataAbsensi>

    @GET("api/mahasiswa/{matakuliahId}")
    fun getMahasiswa(@Path("matakuliahId") matakuliahId: String): Call<ModelDataMahasiswa>
}