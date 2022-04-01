package com.app.absensi.ui.dosen.presensi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.absensi.adapter.ListPertemuanAdapter
import com.app.absensi.databinding.ActivityPertemuanBinding
import com.app.absensi.data.Mahasiswa
import com.app.absensi.data.Matakuliah
import com.app.absensi.data.response.MatakuliahResponse
import com.app.absensi.ui.dosen.daftarpresensi.DetailPertemuanActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class PertemuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertemuanBinding
    private lateinit var matakuliah: MatakuliahResponse
    private var listPresensi = ArrayList<Mahasiswa>()
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
                    pertemuan = position

                    Toast.makeText(this@PertemuanActivity, (pertemuan+1).toString(), Toast.LENGTH_SHORT).show()

                    val options = ScanOptions()
                    barcodeLauncher.launch(options)
                } else {
//                    val intent = Intent(this@PertemuanActivity, DetailPertemuanActivity::class.java)
//                    intent.putExtra("MATAKULIAH", matakuliah)
//                    intent.putExtra("PERTEMUAN", position)
//                    startActivity(intent)
                    Toast.makeText(this@PertemuanActivity, (position+1).toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents != null) {
            Toast.makeText(this@PertemuanActivity, result.contents, Toast.LENGTH_SHORT).show()
        }
    }
}