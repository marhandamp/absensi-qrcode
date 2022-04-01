package com.app.absensi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
//import com.app.absensi.adapter.ListMatakuliahAdapter
import com.app.absensi.databinding.ActivityMainBinding
import com.app.absensi.ui.admin.LoginAdminActivity
import com.app.absensi.ui.dosen.DosenActivity
import com.app.absensi.ui.dosen.LoginDosenActivity
import com.app.absensi.ui.mahasiswa.SearchQrCodeActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        Log.d("Irwandi", sessionManager.fetchAuthToken().toString())

        if(sessionManager.fetchAuthToken() != null){
            val intent = Intent(this@MainActivity, DosenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnMahasiswa.setOnClickListener(this)
        binding.btnDosen.setOnClickListener(this)
        binding.btnAdmin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_mahasiswa -> {
                Intent(this@MainActivity, SearchQrCodeActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_dosen -> {
                val intent = Intent(this@MainActivity, LoginDosenActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_admin -> {
                Intent(this@MainActivity, LoginAdminActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}