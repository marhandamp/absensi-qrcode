package com.app.absensi.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatakuliahResponse (
    var id: Int? = null,
    var uuid: String? = null,
    var ruangan: String? = null,
    var jam: String? = null,
    var namaMatakuliah: String? = null,
    var jumlahPertemuan: String? = null,
    var kelas: String? = null,
    var tanggalMulai: String? = null,
    var tanggalSelesai: String? = null,
) : Parcelable
