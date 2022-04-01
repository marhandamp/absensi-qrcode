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
import com.app.absensi.ui.dosen.daftarpresensi.DetailPertemuanActivity
import com.google.firebase.database.*
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class PertemuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPertemuanBinding
    private lateinit var matakuliah: Matakuliah
    private var listPresensi = ArrayList<Mahasiswa>()
    private lateinit var daftarPresensiRef: DatabaseReference
    private var kodeFitur: Int = 0
    private var pertemuan: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPertemuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kodeFitur = intent.getIntExtra("KODE_FITUR", 0)
        matakuliah = intent.getParcelableExtra<Matakuliah>("MATAKULIAH")!!
        daftarPresensiRef = FirebaseDatabase.getInstance().getReference("daftarPresensi")

        binding.rvPertemuan.layoutManager = LinearLayoutManager(this)
        val adapter = ListPertemuanAdapter(matakuliah, kodeFitur)
        binding.rvPertemuan.adapter = adapter

        onClick(adapter)
    }

    private fun onClick(adapter: ListPertemuanAdapter){
        adapter.setOnItemClickCallback(object : ListPertemuanAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, matakuliah: Matakuliah, kodeFitur: Int) {
                if (kodeFitur == 1){
                    pertemuan = position

                    Toast.makeText(this@PertemuanActivity, pertemuan.toString(), Toast.LENGTH_LONG).show()

                    val options = ScanOptions()
                    barcodeLauncher.launch(options)
                } else {
                    val intent = Intent(this@PertemuanActivity, DetailPertemuanActivity::class.java)
                    intent.putExtra("MATAKULIAH", matakuliah)
                    intent.putExtra("PERTEMUAN", position)
                    startActivity(intent)
                }
            }
        })
    }

    val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents != null) {
            listPresensi.clear()
            var nim: Long = 0
            val key = daftarPresensiRef.push().key.toString()

            val mahasiswa = Mahasiswa()
            mahasiswa.id = key
            mahasiswa.nim = result.contents.toLong()
            mahasiswa.keterangan = "Hadir"

            daftarPresensiRef.child(matakuliah.id.toString()).child(pertemuan.toString()).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (a in snapshot.children){
                            val data = a.getValue(Mahasiswa::class.java)!!
                            listPresensi.add(data)
                        }
                    }
                    if (listPresensi.isNotEmpty()){
                        for(a in listPresensi.indices){
                            if (listPresensi[a].nim == result.contents.toLong()){
                                nim = result.contents.toLong()
                            }
                        }

                        if (nim == result.contents.toLong()){
                            Toast.makeText(this@PertemuanActivity, "You Were Present", Toast.LENGTH_LONG).show()
                        } else {
                            daftarPresensiRef.child(matakuliah.id.toString()).child(pertemuan.toString()).child(key).setValue(mahasiswa).addOnSuccessListener {
                                Toast.makeText(this@PertemuanActivity, "You're Present Now ${result.contents.toLong()}", Toast.LENGTH_LONG).show()
                            }.addOnFailureListener {
                                Toast.makeText(this@PertemuanActivity, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        daftarPresensiRef.child(matakuliah.id.toString()).child(pertemuan.toString()).child(key).setValue(mahasiswa).addOnSuccessListener {
                            Toast.makeText(this@PertemuanActivity, "You're Present Now ${result.contents.toLong()}", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {
                            Toast.makeText(this@PertemuanActivity, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@PertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun getDaftarPresensi(){
        daftarPresensiRef.child(matakuliah.id.toString()).child(pertemuan.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (a in snapshot.children){
                        val data = a.getValue(Mahasiswa::class.java)!!
                        listPresensi.add(data)
                    }
                }
                Log.d("CObaa", listPresensi.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}