package com.app.absensi.ui.dosen.daftarpresensi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.absensi.adapter.ListPresensiAdapter
import com.app.absensi.databinding.ActivityDetailPertemuanBinding
import com.app.absensi.data.Mahasiswa
import com.app.absensi.data.Matakuliah
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class DetailPertemuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPertemuanBinding
    private lateinit var adapter: ListPresensiAdapter
    private lateinit var matakuliah: Matakuliah
    private var pertemuan: Int = 1
    private lateinit var daftarMahasiswaRef: DatabaseReference
    private lateinit var daftarPresensiRef: DatabaseReference
    private var listMahasiswa = ArrayList<Mahasiswa>()
    private var listPresensi = ArrayList<Mahasiswa>()
//    private var sum by Delegates.notNull<Int>()
//    private var dataAttendanceSize by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPertemuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daftarMahasiswaRef = FirebaseDatabase.getInstance().getReference("daftarMahasiswa")
        daftarPresensiRef = FirebaseDatabase.getInstance().getReference("daftarPresensi")
        matakuliah = intent.getParcelableExtra("MATAKULIAH")!!
        pertemuan = intent.getIntExtra("PERTEMUAN", 1)

        getDaftarPresensi()
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
                getDaftarMahasiswa(listPresensi)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDaftarMahasiswa(listPresensi: ArrayList<Mahasiswa>){
        daftarMahasiswaRef.child(matakuliah.id.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (a in snapshot.children){
                        val data = a.getValue(Mahasiswa::class.java)!!
                        listMahasiswa.add(data)
                    }
                }

                binding.rvPertemuan.layoutManager = LinearLayoutManager(this@DetailPertemuanActivity)
                adapter = ListPresensiAdapter(listMahasiswa, listPresensi, pertemuan, daftarPresensiRef, matakuliah.id.toString())
                binding.rvPertemuan.adapter = adapter

//                Log.d("Irwandi Item5", listMahasiswa.toString())
//                Log.d("Irwandi Item6", listPresensi.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun getDaftarPresensi(){
//        daftarPresensiRef.child(matakuliah.id.toString()).child(pertemuan.toString()).addValueEventListener(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    for (a in snapshot.children){
//                        val data = a.getValue(Mahasiswa::class.java)!!
//                        listPresensi.add(data)
//                    }
//                }
//                getDaftarMahasiswa(listPresensi)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@DetailPertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun getDaftarMahasiswa(listPresensi: ArrayList<Mahasiswa>){
//        daftarMahasiswaRef.child(matakuliah.id.toString()).addValueEventListener(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    for (a in snapshot.children){
//                        val data = a.getValue(Mahasiswa::class.java)!!
//                        listMahasiswa.add(data)
//                    }
//                }
//
//                Log.d("Kanesten", listPresensi.toString())
//                Log.d("Kanesten2", listMahasiswa.toString())
//                binding.rvPertemuan.layoutManager = LinearLayoutManager(this@DetailPertemuanActivity)
//                adapter = ListPresensiAdapter(listMahasiswa, listPresensi, pertemuan, daftarPresensiRef, matakuliah.id.toString())
//                binding.rvPertemuan.adapter = adapter
//
////                Log.d("Irwandi Item5", listMahasiswa.toString())
////                Log.d("Irwandi Item6", listPresensi.toString())
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@DetailPertemuanActivity, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailPertemuanBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        absensiRef = FirebaseDatabase.getInstance().getReference("Absensi")
//        sum = intent.getIntExtra("SUM", 0)
//        dataMatakuliah = intent.getParcelableExtra("DATA_ATTENDANCE")!!
//        dataAttendanceSize = dataMatakuliah.daftarMahasiswa?.size as Int
//
//        binding.rvPertemuan.layoutManager = LinearLayoutManager(this)
//
////        val stringBuilder = StringBuilder()
////        for (i in 0 until dataAttendanceSize){
//////            stringBuilder.append(dataAttendance.daftarMahasiswa!![i].nim)
////        }
////
////        binding.tvPresent.text = stringBuilder
//        getDataMatakuliah(absensiRef)
//    }
//
//    private fun getDataMatakuliah(ref: DatabaseReference) {
//        val namaMatkulKelas = cleanString("${dataMatakuliah.namaMatakuliah}-${dataMatakuliah.kelas}")
//
//        ref.child(dataMatakuliah.idDosen.toString()).child(namaMatkulKelas).child(addDate(dataMatakuliah.tanggalMasuk.toString(),sum)).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                listStatusMahasiswa = ArrayList()
//                if (snapshot.exists()){
////                    for (a in 0 until dataAttendanceSize){
//                        for (i in snapshot.children){
//                            val data = i.getValue(Status::class.java) as Status
////                            if (data.nim == dataAttendance.daftarMahasiswa?.get(a)?.nim){
////
//                                listStatusMahasiswa.add(data)
////                            }
//                        }
////                    }
//
//                } else {
//                    Toast.makeText(this@DetailPertemuanActivity, "Pertemuan Belum Dimulai", Toast.LENGTH_SHORT).show()
//                    Log.d("Irwandi", "Data tidak Ada")
//                }
////                Log.d("Irwandi-mahasiswaList", mahasiswaList.toString())
//                pertemuanAdapter = PertemuanAdapter(dataMatakuliah.daftarMahasiswa!!, listStatusMahasiswa, addDate(dataMatakuliah.tanggalMasuk.toString(),sum))
//                binding.rvPertemuan.adapter = pertemuanAdapter
//
////                val stringBuilder = StringBuilder()
////                for (i in 0 until mahasiswaList.size){
////                    stringBuilder.append("${mahasiswaList[i].nim}\n")
////                }
//
////                binding.tvPresent.text = stringBuilder
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("Irwandi-Error", error.message)
//            }
//        })
//    }
//
//    private fun cleanString(data: String): String {
//        val removeSpace = data.replace("\\s".toRegex(), "")
//        val removeColon = removeSpace.replace(":", "")
//        return removeColon.replace(".", "")
//    }
//
//    private fun addDate(date: String, sum: Int): String {
//        var dt = date
//
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val c = Calendar.getInstance()
//        c.time = sdf.parse(dt)
//        c.add(Calendar.DATE, sum) // number of days to add
//
//        dt = sdf.format(c.time) // dt is now the new date
//
//        return dt
//    }



}