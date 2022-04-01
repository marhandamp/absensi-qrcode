package com.app.absensi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.absensi.R
import com.app.absensi.data.response.MatakuliahResponse
import com.app.absensi.databinding.ItemMatakuliahBinding

class ListMatakuliahAdapter(private val listMatakuliah: ArrayList<MatakuliahResponse>, private val context: Context, private val kodeFitur: Int): RecyclerView.Adapter<ListMatakuliahAdapter.AttendanceViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMatakuliahBinding.bind(itemView)
        fun bind(matakuliah: MatakuliahResponse, kodeFitur: Int) {
//            val namaDosen = StringBuilder()
//            val size = matakuliah.dosen!!.size
//
//            for (a in matakuliah.dosen!!.indices){
//                namaDosen.append(matakuliah.dosen!![a])
//                if (a != size-1){
//                    namaDosen.append(", ")
//                }
//            }

            binding.tvNamaMatakuliah.text = matakuliah.namaMatakuliah
            binding.tvRuangan.text = "Ruangan ${matakuliah.ruangan}"
            binding.tvPukul.text = "Pukul ${matakuliah.jam}"
            binding.tvKelas.text = matakuliah.kelas
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(matakuliah, kodeFitur) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matakuliah, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        return holder.bind(listMatakuliah[position], kodeFitur)
    }

    override fun getItemCount(): Int {
        return listMatakuliah.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(matakuliah: MatakuliahResponse, kodeFitur: Int)
    }
}