package com.app.absensi.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.absensi.R
import com.app.absensi.SessionManager
import com.app.absensi.data.model.ModelLoginAdmin
import com.app.absensi.data.model.ModelLoginDosen
import com.app.absensi.data.request.LoginAdminRequest
import com.app.absensi.data.request.LoginDosenRequest
import com.app.absensi.databinding.ActivityLoginAdminBinding
import com.app.absensi.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAdminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        auth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_sign_in -> {
                loading(true)
                val email = binding.edtEmailAdmin.text.toString().trim()
                val password = binding.edtPassword.text.toString().trim()

                if (email.isEmpty()){
                    loading(false)
                    binding.edtEmailAdmin.error = "Please Enter Nim"
                    binding.edtEmailAdmin.requestFocus()
                    return
                }

                if (password.isEmpty()){
                    loading(false)
                    binding.edtPassword.error = "Please Enter Nim"
                    binding.edtPassword.requestFocus()
                    return
                }

                val loginRequest = LoginAdminRequest()
                loginRequest.email = email
                loginRequest.password = password

                postLoginAdminApi(loginRequest)
            }
        }
    }

    private fun postLoginAdminApi(loginAdminRequest: LoginAdminRequest){
        RetrofitClient.instance(this).postLoginAdmin(loginAdminRequest).enqueue(object : Callback<ModelLoginAdmin> {
            override fun onResponse(
                call: Call<ModelLoginAdmin>,
                response: Response<ModelLoginAdmin>
            ) {
                loading(false)
                val code = response.code()
                val data = response.body()
                if (code == 200){
                    sessionManager.saveAuthTokenAndIdUser(data!!.token!!, data.user!!.id!!, "ADMIN")
                    Intent(this@LoginAdminActivity, AdminActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    loading(false)
                    Toast.makeText(this@LoginAdminActivity, "Email/Password Salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelLoginAdmin>, t: Throwable) {
                loading(false)
                Toast.makeText(this@LoginAdminActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("Irwandi", t.message.toString())
            }

        })
    }

    fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}