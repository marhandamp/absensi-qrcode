package com.app.absensi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.absensi.R
import com.app.absensi.databinding.ItemPertemuan2Binding
import com.app.absensi.data.Matakuliah

class ListPertemuanAdapter(private val matakuliah: Matakuliah, private val kodeFitur: Int): RecyclerView.Adapter<ListPertemuanAdapter.AbsensiPertemuanViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class AbsensiPertemuanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPertemuan2Binding.bind(itemView)
        fun bind(matakuliah: Matakuliah ,kodeFitur: Int) {
            binding.tvPertemuan.text = "Pertemuan ${adapterPosition + 1}"

            binding.root.setOnClickListener { onItemClickCallback.onItemClicked(adapterPosition, matakuliah, kodeFitur)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsensiPertemuanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pertemuan2, parent, false)
        return AbsensiPertemuanViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsensiPertemuanViewHolder, position: Int) {
        return holder.bind(matakuliah, kodeFitur)
    }

    override fun getItemCount(): Int {
        return matakuliah.jumlahPertemuan
    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, matakuliah: Matakuliah ,kodeFitur: Int)
    }
}