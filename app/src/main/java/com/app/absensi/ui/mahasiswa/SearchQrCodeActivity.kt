package com.app.absensi.ui.mahasiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.absensi.R
import com.app.absensi.databinding.ActivitySearchQrCodeBinding
import com.app.absensi.network.RetrofitClient
import com.squareup.picasso.Picasso

class SearchQrCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val nim = binding.edtNim.text.toString()
            val url = "${RetrofitClient.getBaseUrl()}file-image/$nim.jpg"

            Picasso.get()
                .load(url)
                .error(R.drawable.error)
                .into(binding.imgQrCode)
        }
    }
}