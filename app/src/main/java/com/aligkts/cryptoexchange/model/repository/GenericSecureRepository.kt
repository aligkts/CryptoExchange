package com.aligkts.cryptoexchange.model.repository

import com.aligkts.cryptoexchange.MainApplication

/**
 * Created by Ali Göktaş on 11.08.2020
 */

interface GenericSecureRepository {
    companion object {
        val default: GenericSecureRepository by lazy {
            SecureSharedPref(context = MainApplication.instance)
        }
    }

    fun put(key: String, value: String)
    fun put(key: String, value: Int)
    fun put(key: String, value: Boolean)
    fun put(key: String, value: Long)
    fun put(key: String, value: Float)
    fun putStringList(key: String, value: ArrayList<String>)

    fun getString(key: String): String?
    fun getInt(key: String): Int?
    fun getBoolean(key: String): Boolean?
    fun getLong(key: String): Long?
    fun getFloat(key: String): Float?
    fun getStringList(key: String): ArrayList<String>

    fun remove(key: String)
    fun contains(key: String): Boolean
}