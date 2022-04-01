package com.app.absensi.ui.mahasiswa

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.absensi.databinding.ActivitySearchQrCodeBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SearchQrCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            loading(true)
            val nim = binding.edtNim.text.toString()

            val storageRef = FirebaseStorage.getInstance().reference.child("images/$nim")

            val localFile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                loading(false)
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.imgQrCode.setImageBitmap(bitmap)
            }.addOnFailureListener() {
                loading(false)
                Toast.makeText(this@SearchQrCodeActivity, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loading(state: Boolean) {
        if (state){
            binding.pbImage.visibility = View.VISIBLE
        } else {
            binding.pbImage.visibility = View.GONE
        }
    }
}