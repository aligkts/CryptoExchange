package com.aligkts.cryptoexchange.model.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Created by Ali Göktaş on 11.08.2020
 */

class SecureSharedPref(context: Context) : GenericSecureRepository {
    private val sharedPreferences: SharedPreferences
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    init {
        val fileToRead = "securePref.txt"
        sharedPreferences = EncryptedSharedPreferences
            .create(
                fileToRead,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    override fun put(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun put(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun put(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun put(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun put(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun getInt(key: String): Int? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getInt(key, 0) else null
    }

    override fun getBoolean(key: String): Boolean? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getBoolean(
            key,
            false
        ) else null
    }

    override fun getLong(key: String): Long? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getLong(key, 0) else null
    }

    override fun getFloat(key: String): Float? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getFloat(key, 0f) else null
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}