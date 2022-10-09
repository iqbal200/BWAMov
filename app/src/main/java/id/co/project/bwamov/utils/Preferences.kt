package id.co.project.bwamov.utils

import android.content.SharedPreferences
import com.google.firebase.database.core.Context

class Preferences (val context : android.content.Context){
    companion object{
        const val USER_PREFF = "USER_PREFF"
    }
    var sharePreferences = context.getSharedPreferences(USER_PREFF,0)

    fun setValue(key : String, value : String){
        val editor:SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun  getValue(key: String) : String?{
        return sharePreferences.getString(key, "")
    }
}