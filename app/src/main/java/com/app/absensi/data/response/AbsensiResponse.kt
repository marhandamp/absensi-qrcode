package com.app.absensi.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AbsensiResponse {
    var id: Int? = null
    var nim: String? = null
    var keterangan: String? = null
    var pertemuan: String? = null

    @SerializedName("matakuliah_id")
    @Expose
    var matakuliahId: String? = null
}