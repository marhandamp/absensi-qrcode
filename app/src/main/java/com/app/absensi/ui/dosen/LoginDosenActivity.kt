package com.app.absensi.ui.dosen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.absensi.R
import com.app.absensi.SessionManager
import com.app.absensi.databinding.ActivityLoginDosenBinding
import com.app.absensi.data.request.LoginDosenRequest
import com.app.absensi.data.model.ModelLoginDosen
import com.app.absensi.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDosenActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginDosenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDosenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(this)
//        if(sessionManager.fetchAuthToken() != null){
//            val intent = Intent(this, LoginDosenActivity::class.java)
//            startActivity(intent)
//        }

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
                    binding.edtEmailAdmin.error = "Please Enter Email"
                    binding.edtEmailAdmin.requestFocus()
                    return
                }

                if (password.isEmpty()){
                    loading(false)
                    binding.edtPassword.error = "Please Enter Password"
                    binding.edtPassword.requestFocus()
                    return
                }

                val loginRequest = LoginDosenRequest()
                loginRequest.email = email
                loginRequest.password = password

                postLoginApi(loginRequest)
            }
        }
    }

    private fun postLoginApi(loginDosenRequest: LoginDosenRequest){
        RetrofitClient.instance(this).postLoginDosen(loginDosenRequest).enqueue(object : Callback<ModelLoginDosen> {
            override fun onResponse(
                call: Call<ModelLoginDosen>,
                response: Response<ModelLoginDosen>
            ) {
                loading(false)
                val code = response.code()
                val data = response.body()
                if (code == 200){
                    sessionManager.saveAuthTokenAndIdUser(data!!.token!!, data.user!!.id!!, "DOSEN")
                    Intent(this@LoginDosenActivity, DosenActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    loading(false)
                    Toast.makeText(this@LoginDosenActivity, "Email/Password Salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelLoginDosen>, t: Throwable) {
                loading(false)
                Toast.makeText(this@LoginDosenActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("Irwandi", t.message.toString())
            }
        })
    }

    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}