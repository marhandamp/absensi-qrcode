package com.app.absensi.ui.admin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.app.absensi.MainActivity
import com.app.absensi.R
import com.app.absensi.SessionManager
import com.app.absensi.data.model.ModelDataQRCode
import com.app.absensi.data.response.LogoutAdminResponse
import com.app.absensi.databinding.ActivityAdminBinding
import com.app.absensi.databinding.ActivityMainBinding
import com.app.absensi.network.RetrofitClient
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.Exception

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerateQrCode.setOnClickListener {
            loading(true)
            binding.imgQrCode.setImageBitmap(null)
            try {
                val input = binding.edtInput.text.toString()
                if (input.isEmpty()){
                    loading(false)
                    binding.edtInput.error = "Field Can't Be Empty"
                } else {
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap = barcodeEncoder.encodeBitmap(input, BarcodeFormat.QR_CODE, 850, 850)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    val requestBody = RequestBody.create("multipart/form-file".toMediaTypeOrNull(), data)
                    val image = MultipartBody.Part.createFormData("qrcode_img", "$input.jpg", requestBody)

                    RetrofitClient.instance(this).postQRCode(image).enqueue(object : Callback<ModelDataQRCode> {
                        override fun onResponse(
                            call: Call<ModelDataQRCode>,
                            response: Response<ModelDataQRCode>
                        ) {
                            if (response.isSuccessful){
                                loading(false)
                                binding.imgQrCode.setImageBitmap(bitmap)
                            } else {
                                Log.e("Irwandi", response.message())
                            }
                        }

                        override fun onFailure(call: Call<ModelDataQRCode>, t: Throwable) {
                            loading(false)
                            t.message?.let { it1 -> Log.e("Irwandi", it1) }
                            Toast.makeText(this@AdminActivity, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
            } catch (e: Exception) {
                loading(false)
                e.message?.let { it1 -> Log.e("Irwandi", it1) }
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.signout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_signout){
            postLogoutAdminApi()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun postLogoutAdminApi(){
        val progressDialog = ProgressDialog(this@AdminActivity)
        progressDialog.setMessage("Tunggu..")
        progressDialog.setCancelable(false)
        progressDialog.show()
        RetrofitClient.instance(this).postLogoutAdmin().enqueue(object : Callback<LogoutAdminResponse> {
            override fun onResponse(
                call: Call<LogoutAdminResponse>,
                response: Response<LogoutAdminResponse>
            ) {
                when(response.code()){
                    200 -> {
                        progressDialog.dismiss()
                        val sessionManager = SessionManager(this@AdminActivity)
                        sessionManager.deleteAuthTokenIdUserAndStatusUser()
                        Intent(this@AdminActivity, MainActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(it)
                        }
                    }
                    else -> {
                        progressDialog.dismiss()
                        Toast.makeText(this@AdminActivity, "Ada yang tidak beres\n${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LogoutAdminResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@AdminActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loading(state: Boolean){
        if(state){
            binding.pbGenerateQrCode.visibility = View.VISIBLE
        } else {
            binding.pbGenerateQrCode.visibility = View.GONE
        }
    }
}