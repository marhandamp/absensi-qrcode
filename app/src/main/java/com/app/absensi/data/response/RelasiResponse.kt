package com.app.absensi.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelasiResponse {
    var id: Int? = null

    @SerializedName("matakuliah_id")
    @Expose
    var matakuliahId: String? = null

    @SerializedName("dosen_id")
    @Expose
    var dosenId: Int? = null
}