package com.jcl.exam.emapta.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.jcl.exam.emapta.encryption.EncryptionUtil
import javax.inject.Inject

/**
 * Created by jaylumba on 05/16/2018.
 */

class SecuredPrefs @Inject
constructor(context: Context) {

    private val SHARED_PREF_NAME = "TempPrefsName"
    private val mPref: SharedPreferences?
    private var mEditor: SharedPreferences.Editor? = null

    init {
        mPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref!!.edit().clear().apply()
    }

    fun save(key: String, value: String): Boolean {
        if (mPref != null) {
            mEditor = mPref.edit()
            mEditor!!.putString(key, EncryptionUtil.encrypt(value))
            return mEditor!!.commit()
        } else {
            return false
        }
    }

    operator fun get(key: String): String {
        if (mPref != null) {
            val value = mPref.getString(key, "")
            return if (value == "") "" else EncryptionUtil.decrypt(value)
        } else {
            return ""
        }
    }
}
