package com.app.absensi.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.absensi.R
import com.app.absensi.databinding.ActivityLoginAdminBinding
import com.google.firebase.auth.FirebaseAuth

class LoginAdminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        loading(false)

                        Intent(this, AdminActivity::class.java).also { intent ->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        loading(false)
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}