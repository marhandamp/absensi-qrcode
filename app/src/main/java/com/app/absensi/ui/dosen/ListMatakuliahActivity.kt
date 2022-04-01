package com.app.absensi.ui.dosen

import androidx.appcompat.app.AppCompatActivity
import com.app.absensi.adapter.ListMatakuliahAdapter
import com.app.absensi.databinding.ActivityListMatakuliahBinding
import com.app.absensi.data.Matakuliah
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ListMatakuliahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMatakuliahBinding
    private lateinit var matakuliahref: DatabaseReference
    private lateinit var auth: FirebaseUser
    private lateinit var matakuliahList: ArrayList<Matakuliah>
    private lateinit var listMatakuliahAdapter: ListMatakuliahAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityListMatakuliahBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        auth = FirebaseAuth.getInstance().currentUser!!
//        binding.rvAttendaceListForDosen.layoutManager = LinearLayoutManager(this)
//        matakuliahref = FirebaseDatabase.getInstance().getReference("Matakuliah")
//
//        matakuliahref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                matakuliahList = ArrayList()
//                if (snapshot.exists()){
//                    for (i in snapshot.children){
//                        val attendance = i.getValue(Matakuliah::class.java)!!
//                        val idDosen = attendance.idDosen.toString()
//
//                        if (idDosen == auth.uid){
//                            matakuliahList.add(attendance)
//                        }
//                    }
//                } else {
//                    Log.d("Irwandi", "Data tidak Ada")
//                }
//                Log.d("Irwandi", matakuliahList.toString())
//                attendanceAdapter = AttendanceAdapter(matakuliahList, this@ListMatakuliahActivity)
//                binding.rvAttendaceListForDosen.adapter = attendanceAdapter
//
//                onCLick()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("Irwandi", "Data tidak Ada")
//                Toast.makeText(this@ListMatakuliahActivity, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

//    private fun onCLick() {
//        attendanceAdapter.setOnItemClickCallback(object : AttendanceAdapter.OnItemClickCallback {
//            override fun onItemClicked(matakuliah: Matakuliah) {
//                Intent(this@ListMatakuliahActivity, PertemuanActivity::class.java).also {
//                    it.putExtra("EXTRA_ATTENDANCE", matakuliah)
//                    startActivity(it)
//                }
//
////                Intent(this@DosenActivity, ListPertemuanActivity::class.java).also {
////                    it.putExtra("EXTRA_ATTENDANCE", attendance)
////                    startActivity(it)
////                }
//            }
//        })
//    }
}