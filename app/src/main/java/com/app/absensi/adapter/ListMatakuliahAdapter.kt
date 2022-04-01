package com.app.absensi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.absensi.R
import com.app.absensi.databinding.ItemAttendanceBinding
import com.app.absensi.data.Matakuliah

class ListMatakuliahAdapter(private val listMatakuliah: ArrayList<Matakuliah>, private val context: Context, private val kodeFitur: Int): RecyclerView.Adapter<ListMatakuliahAdapter.AttendanceViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAttendanceBinding.bind(itemView)
        fun bind(matakuliah: Matakuliah, kodeFitur: Int) {
            val namaDosen = StringBuilder()
            val size = matakuliah.dosen!!.size

            for (a in matakuliah.dosen!!.indices){
                namaDosen.append(matakuliah.dosen!![a])
                if (a != size-1){
                    namaDosen.append(", ")
                }
            }

            binding.tvNamaDosen.text = namaDosen
            binding.tvMatakuliah.text = matakuliah.namaMatakuliah
            binding.tvKelas.text = matakuliah.kelas
            binding.tvJamMasuk.text = matakuliah.jam
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(matakuliah, kodeFitur) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        return holder.bind(listMatakuliah[position], kodeFitur)
    }

    override fun getItemCount(): Int {
        return listMatakuliah.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(matakuliah: Matakuliah, kodeFitur: Int)
    }
}