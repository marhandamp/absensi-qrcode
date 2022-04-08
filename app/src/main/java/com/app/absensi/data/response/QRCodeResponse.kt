package com.app.absensi.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QRCodeResponse {
    var id: Int? = null

    @SerializedName("qrcode_img")
    @Expose
    var imgQRCode: String? = null
}