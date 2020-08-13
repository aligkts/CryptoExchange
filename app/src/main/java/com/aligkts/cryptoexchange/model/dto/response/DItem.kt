package com.aligkts.cryptoexchange.model.dto.response

import android.os.Parcelable
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.RecyclerViewItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
) : Parcelable, RecyclerViewItem(R.layout.item_coin_detail)