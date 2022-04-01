package com.app.absensi.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mahasiswa(
    var id: String? = null,
    var nama: String? = null,
    var nim: Long? = null,
    var keterangan: String? = null
) : Parcelable
