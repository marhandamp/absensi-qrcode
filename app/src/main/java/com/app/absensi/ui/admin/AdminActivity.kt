package com.app.absensi.ui.admin

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.absensi.databinding.ActivityAdminBinding
import com.app.absensi.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
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
//                    loading(false)
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap = barcodeEncoder.encodeBitmap(input, BarcodeFormat.QR_CODE, 850, 850)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    val storageRef = FirebaseStorage.getInstance().getReference("images/$input")

                    val uploadTask = storageRef.putBytes(data)
                    uploadTask.addOnFailureListener {
                        loading(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }.addOnSuccessListener {
                        loading(false)
                        binding.imgQrCode.setImageBitmap(bitmap)

                    }
                }
            } catch (e: Exception) {
                loading(false)
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loading(state: Boolean){
        if(state){
            binding.pbGenerateQrCode.visibility = View.VISIBLE
        } else {
            binding.pbGenerateQrCode.visibility = View.GONE
        }
    }
}