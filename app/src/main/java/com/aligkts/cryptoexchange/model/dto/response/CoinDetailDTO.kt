package com.aligkts.cryptoexchange.model.dto.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinDetailDTO(

	@SerializedName("d")
	val items: List<DItem>

) : Parcelable





























