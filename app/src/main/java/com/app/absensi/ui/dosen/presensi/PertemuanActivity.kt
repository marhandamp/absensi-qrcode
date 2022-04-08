package com.app.absensi.ui.dosen.presensi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.absensi.adapter.ListPertemuanAdapter
import com.app.absensi.databinding.ActivityPertemuanBinding
import com.app.absensi.data.model.ModelDataAbsensi
import com.app.absensi.data.request.AbsensiRequest
import com.app.absensi.data.response.MahasiswaResponse
import com.app.absensi.data.response.MatakuliahResponse
import com.app.absensi.network.RetrofitClient
import com.app.absensi.ui.dosen.daftarpresensi.DetailPertemuanActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PertemuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertemuanBinding
    private lateinit var matakuliah: MatakuliahResponse
    private var matakuliahId: Int = 0
    private var listPresensi = ArrayList<MahasiswaResponse>()
    private var kodeFitur: Int = 0
    private var pertemuan: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertemuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kodeFitur = intent.getIntExtra("KODE_FITUR", 0)
        matakuliah = intent.getParcelableExtra("MATAKULIAH")!!

        binding.rvPertemuan.layoutManager = LinearLayoutManager(this)
        val adapter = ListPertemuanAdapter(matakuliah, kodeFitur)
        binding.rvPertemuan.adapter = adapter

        onClick(adapter)
    }

    private fun onClick(adapter: ListPertemuanAdapter){
        adapter.setOnItemClickCallback(object : ListPertemuanAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, matakuliah: MatakuliahResponse, kodeFitur: Int) {
                if (kodeFitur == 1){
                    pertemuan = position+1
                    matakuliahId = matakuliah.id!!

                    val options = ScanOptions()
                    barcodeLauncher.launch(options)
                } else {
                    val intent = Intent(this@PertemuanActivity, DetailPertemuanActivity::class.java)
                    intent.putExtra("MATAKULIAH", matakuliah)
                    intent.putExtra("PERTEMUAN", position)
                    Log.e("Irwandi", matakuliah.toString())
                    Log.e("Irwandi", position.toString())
                    startActivity(intent)
                }
            }
        })
    }

    val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents != null) {
            postAbsensiApi(result.contents, "Hadir", pertemuan.toString(), matakuliahId.toString())
        }
    }

    private fun postAbsensiApi(nim: String, ket: String, pertemuan: String, matkulId: String){
        val absensiRequest = AbsensiRequest()
        absensiRequest.nim = nim
        absensiRequest.keterangan = ket
        absensiRequest.pertemuan = pertemuan
        absensiRequest.matakuliahId = matkulId

        RetrofitClient.instance(this).postAbsensi(absensiRequest).enqueue(object : Callback<ModelDataAbsensi> {
            override fun onResponse(
                call: Call<ModelDataAbsensi>,
                response: Response<ModelDataAbsensi>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@PertemuanActivity, "Berhasil Absen $nim", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PertemuanActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelDataAbsensi>, t: Throwable) {
                Toast.makeText(this@PertemuanActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}