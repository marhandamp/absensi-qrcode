package com.app.absensi.ui.dosen.presensi

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.absensi.SessionManager
import com.app.absensi.adapter.ListMatakuliahAdapter
import com.app.absensi.databinding.ActivityMatakuliahBinding
import com.app.absensi.data.model.ModelDataMatakuliah
import com.app.absensi.data.model.ModelDataRelasi
import com.app.absensi.data.response.MatakuliahResponse
import com.app.absensi.data.response.RelasiResponse
import com.app.absensi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MatakuliahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatakuliahBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: ListMatakuliahAdapter
    private var kodeFitur: Int = 0
    private val relasiResponse = ArrayList<RelasiResponse>()
    private val matakuliahResponse = ArrayList<MatakuliahResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatakuliahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        kodeFitur = intent.getIntExtra("KODE_FITUR", 0)

        getRelasiApi()

//        onCLick()

        binding.rvAttendaceList.layoutManager = LinearLayoutManager(this)


    }

    private fun getRelasiApi(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Memuat Data..")
        progressDialog.setCancelable(false)
        progressDialog.show()

        RetrofitClient.instance(this).getRelasi().enqueue(object : Callback<ModelDataRelasi> {
            override fun onResponse(
                call: Call<ModelDataRelasi>,
                response: Response<ModelDataRelasi>
            ) {
                val code = response.code()
                val data = response.body()
                val idUser = sessionManager.fetchIdUser()
                if (code == 200){
                    for (a in data!!.data!!.indices){
                        if (idUser == data.data!![a].dosenId){
                            relasiResponse.add(data.data!![a])
                        }
                    }
                    getMatakuliahApi(progressDialog)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@MatakuliahActivity, "Ada yang tidak beres\n${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelDataRelasi>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@MatakuliahActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getMatakuliahApi(progressDialog: ProgressDialog) {
        RetrofitClient.instance(this).getMatakuliah().enqueue(object :
            Callback<ModelDataMatakuliah> {
            override fun onResponse(
                call: Call<ModelDataMatakuliah>,
                response: Response<ModelDataMatakuliah>
            ) {
                val data = response.body()
                val code = response.code()
                if (code == 200){
                    for(a in data!!.data!!.indices){
                        for (b in relasiResponse.indices){
                            if (relasiResponse[b].matakuliahId == data.data!![a].uuid){
                                matakuliahResponse.add(data.data!![a])
                            }
                        }
                    }
                    adapter = ListMatakuliahAdapter(matakuliahResponse, this@MatakuliahActivity, kodeFitur)
                    binding.rvAttendaceList.adapter = adapter
                    progressDialog.dismiss()
                    onCLick()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@MatakuliahActivity, "Ada yang tidak beres\n${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("Irwandi", "Ada yang tidak beres\n${response.message()}")
                }
            }

            override fun onFailure(call: Call<ModelDataMatakuliah>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@MatakuliahActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.e("Irwandi", t.message.toString())
            }
        })
    }

    private fun onCLick() {
        adapter.setOnItemClickCallback(object : ListMatakuliahAdapter.OnItemClickCallback {
            override fun onItemClicked(matakuliah: MatakuliahResponse, kodeFitur: Int) {
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