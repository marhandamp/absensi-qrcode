package com.app.absensi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.absensi.R
import com.app.absensi.databinding.ItemPertemuanBinding
import com.app.absensi.data.response.MahasiswaResponse
import com.google.firebase.database.DatabaseReference
import kotlin.collections.ArrayList

class ListPresensiAdapter(
    private val listMahasiswa: ArrayList<MahasiswaResponse>,
//    private val listPresensi: ArrayList<Mahasiswa>,
    private val pertemuan: Int
//    private val daftarPresensiRef: DatabaseReference,
//    private val matakuliahId: String
    ): RecyclerView.Adapter<ListPresensiAdapter.PertemuanViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class PertemuanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPertemuanBinding.bind(itemView)
        fun bind(mahasiswa: MahasiswaResponse, pertemuan: Int) {
            binding.tvNomor.text = "${adapterPosition+1}."
            binding.tvNim.text = mahasiswa.nim.toString()
            binding.tvName.text = mahasiswa.nama

//            for (a in listPresensi.indices){
//                if (mahasiswa.nim == listPresensi[a].nim){
//                    binding.tvKeterangan.text = listPresensi[a].keterangan
//                    binding.tvKeterangan.visibility = View.VISIBLE
//                    binding.btnSakit.visibility = View.GONE
//                    binding.btnIzin.visibility = View.GONE
//                    binding.btnAlpha.visibility = View.GONE
//                }
//            }

//            binding.btnSakit.setOnClickListener { onClick(binding, "Sakit", pertemuan, itemView.context, mahasiswa) }
//            binding.btnAlpha.setOnClickListener { onClick(binding, "Alpha", pertemuan, itemView.context, mahasiswa) }
//            binding.btnIzin.setOnClickListener { onClick(binding, "Izin", pertemuan, itemView.context, mahasiswa) }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PertemuanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pertemuan, parent, false)
        return PertemuanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PertemuanViewHolder, position: Int) {
        return holder.bind(listMahasiswa[position], pertemuan)
    }

    override fun getItemCount(): Int {
        return listMahasiswa.size
    }

//    private fun onClick(binding: ItemPertemuanBinding, keterangan: String, pertemuan: Int, context: Context, mahasiswa: Mahasiswa){
//        val key = daftarPresensiRef.push().key.toString()
//        val data = Mahasiswa()
//        data.id = key
//        data.nim = mahasiswa.nim
//        data.keterangan = keterangan
//
//        daftarPresensiRef.child(matakuliahId).child(pertemuan.toString()).child(key).setValue(data).addOnFailureListener {
//            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//        }.addOnSuccessListener {
////            Log.d("Irwandi Item", listMahasiswa.toString())
////            Log.d("Irwandi Item2", listPresensi.toString())
////            listMahasiswa.clear()
////            listPresensi.clear()
//            Log.d("Irwandi Item3", listMahasiswa.toString())
//            Log.d("Irwandi Item4", listPresensi.toString())
//        }
//        binding.tvKeterangan.text = keterangan
//        binding.tvKeterangan.visibility = View.VISIBLE
//        binding.btnSakit.visibility = View.GONE
//        binding.btnIzin.visibility = View.GONE
//        binding.btnAlpha.visibility = View.GONE
//    }

    interface OnItemClickCallback {
        fun onItemClicked(binding: ItemPertemuanBinding)
    }
}