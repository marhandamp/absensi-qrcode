package com.app.absensi.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Matakuliah(
    var id: String? = null,
    var dosen: ArrayList<String>? = null,
    var kodeDosen: ArrayList<String>? = null,
    var namaMatakuliah: String? = null,
    var jam: String? = null,
    var kelas: String? = null,
    var jumlahPertemuan: Int = 0,
    var ruangan: Int? = null,
    var tanggalMulai: String? = null,
    var tanggalSelesai: String? = null
) : Parcelable
