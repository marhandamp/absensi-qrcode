package com.app.absensi

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val STATUS_USER = "status_user"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthTokenAndIdUser(token: String, idUser: Int, statusUser: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putInt(USER_ID, idUser)
        editor.putString(STATUS_USER, statusUser)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchIdUser(): Int{
        return prefs.getInt(USER_ID, 0)
    }

    fun fetchStatusUser(): String?{
        return prefs.getString(STATUS_USER, null)
    }

    fun deleteAuthTokenIdUserAndStatusUser(){
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove(USER_ID)
        editor.remove(STATUS_USER)
        editor.apply()
    }
}