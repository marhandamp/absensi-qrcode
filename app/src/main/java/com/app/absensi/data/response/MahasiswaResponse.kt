package com.app.absensi.data.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MahasiswaResponse (
    var id: Int? = null,
    var nama: String? = null,
    var nim: String? = null,

    @SerializedName("matakuliah_id")
    @Expose
    var matakuliahId: String? = null
) : Parcelable