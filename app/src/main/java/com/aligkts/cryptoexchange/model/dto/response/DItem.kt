package com.aligkts.cryptoexchange.model.dto.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ali Göktaş on 12,August,2020
 */

@Parcelize
data class DItem(

	@field:SerializedName("clo")
	val clo: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("fields")
	val fields: Fields? = null,

	@field:SerializedName("desc")
	val desc: String? = null
) : Parcelable