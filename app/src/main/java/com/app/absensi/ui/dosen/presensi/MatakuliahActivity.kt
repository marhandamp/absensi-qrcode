package com.app.absensi.ui.dosen.presensi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.absensi.adapter.ListMatakuliahAdapter
import com.app.absensi.databinding.ActivityMatakuliahBinding
import com.app.absensi.data.Matakuliah
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MatakuliahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatakuliahBinding
    private lateinit var matakuliahRef: DatabaseReference
    private lateinit var dosenPengajarRef: DatabaseReference
    private lateinit var matakuliahList: ArrayList<Matakuliah>
    private lateinit var listMatakuliahAdapter: ListMatakuliahAdapter
    private lateinit var data: Matakuliah
    private var kodeFitur: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatakuliahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kodeFitur = intent.getIntExtra("KODE_FITUR", 0)
        binding.rvAttendaceList.layoutManager = LinearLayoutManager(this)
        matakuliahRef = FirebaseDatabase.getInstance().getReference("Matakuliah")
        dosenPengajarRef = FirebaseDatabase.getInstance().getReference("DosenPengajar")

//        getDataMatakuliah(matakuliahRef)
    }

//    fun getMatakuliah(){
//        RetrofitClient.instance(this).getMatakuliah().enqueue(object :
//            Callback<ModelDataMatakuliah> {
//            override fun onResponse(
//                call: Call<ModelDataMatakuliah>,
//                response: Response<ModelDataMatakuliah>
//            ) {
//                val code = response.code()
//                when(code){
//                    200 -> {
//                        for (a in response.body().data.indices){
//
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ModelDataMatakuliah>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

//    private fun getDataMatakuliah(ref: DatabaseReference) {
//        loading(true)
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                matakuliahList = ArrayList()
//                if (snapshot.exists()){
//                    loading(false)
//                    for (i in snapshot.children){
//                        val data = i.getValue(Matakuliah::class.java) as Matakuliah
//                        val dosen = data.kodeDosen
//
//                        for (j in dosen!!.indices){
//                            if (dosen[j] == uid){
//                                matakuliahList.add(data)
//                            }
//                        }
//                    }
//                } else {
//                    loading(false)
//                    Toast.makeText(this@MatakuliahActivity, "Matakuliah Tidak Ada", Toast.LENGTH_SHORT).show()
//                    Log.d("Irwandi", "Data tidak Ada")
//                }
//
//                listMatakuliahAdapter = ListMatakuliahAdapter(matakuliahList, this@MatakuliahActivity, kodeFitur)
//                binding.rvAttendaceList.adapter = listMatakuliahAdapter
//
//                onCLick()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                loading(false)
//                Log.d("Irwandi-Error", error.message)
//            }
//        })
//    }

    private fun onCLick() {
        listMatakuliahAdapter.setOnItemClickCallback(object : ListMatakuliahAdapter.OnItemClickCallback {
            override fun onItemClicked(matakuliah: Matakuliah, kodeFitur: Int) {
                val intent = Intent(this@MatakuliahActivity, PertemuanActivity::class.java)
                intent.putExtra("MATAKULIAH", matakuliah)
                intent.putExtra("KODE_FITUR", kodeFitur)
                startActivity(intent)
            }
        })
    }

//    val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
//        if (result.contents != null) {
//            val id = data.id.toString()
//            val idDosen = data.idDosen
//            val namaMatkul = cleanString(data.namaMatakuliah.toString())
//            val kelas = cleanString(data.kelas.toString())
//            val tanggal = data.tanggal.toString()
//            val nim = mapOf("nim" to result.contents.toString().toLong())
//
//            val absensiRef = FirebaseDatabase.getInstance().getReference("Absensi")
//
//            absensiRef.child(idDosen.toString()).child("${namaMatkul}-$kelas").child(tanggal).push().setValue(nim).addOnSuccessListener {
//                Toast.makeText(this@AttendanceActivity, "You're Present Now", Toast.LENGTH_LONG).show()
//            }.addOnFailureListener {
//                Toast.makeText(this@AttendanceActivity, it.message, Toast.LENGTH_LONG).show()
//            }
//        } else {
//            Toast.makeText(this@AttendanceActivity, "Cancelled", Toast.LENGTH_LONG).show()
//        }
//    }

//    private fun updateDateMatakuliah(id: String, tanggal: String){
//        val matkulRef = FirebaseDatabase.getInstance().getReference("Matakuliah")
//
//        if (currentDateTime("date") == addDate(tanggal)){
//            val updateTanggal = mapOf("tanggal" to addDate(tanggal))
//            matkulRef.child(id).updateChildren(updateTanggal)
//        }
//    }

    private fun cleanString(data: String): String {
        val removeSpace = data.replace("\\s".toRegex(), "")
        val removeColon = removeSpace.replace(":", "")
        return removeColon.replace(".", "")
    }

//    private fun currentDateTime(state: String): String{
//        val date = Calendar.getInstance().time
//
//        return when (state) {
//            "date" -> {
//                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//                sdf.format(date)
//            }
//            "time" -> {
//                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//                sdf.format(date)
//            }
//            else -> {
//                val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
//                sdf.format(date)
//            }
//        }
//    }

    private fun loading(state: Boolean) {
        if(state){
            binding.pbListAttendance.visibility = View.VISIBLE
        } else {
            binding.pbListAttendance.visibility = View.GONE
        }
    }

//    private fun addDate(date: String): String {
//        var dt = date
//
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        val c = Calendar.getInstance()
//        c.time = sdf.parse(dt)
//        c.add(Calendar.DATE, 7) // number of days to add
//
//        dt = sdf.format(c.time) // dt is now the new date
//
//        return dt
//    }

}