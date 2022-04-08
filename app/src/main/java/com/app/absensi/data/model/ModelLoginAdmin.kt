package com.app.absensi.data.model

import com.app.absensi.data.response.LoginAdminResponse
import com.app.absensi.data.response.LoginDosenResponse

class ModelLoginAdmin {
    var code: String? = null
    var message: String? = null
    var token: String? = null
    var user: LoginAdminResponse? = null
}