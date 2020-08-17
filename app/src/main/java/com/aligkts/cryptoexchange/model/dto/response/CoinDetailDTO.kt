package com.aligkts.cryptoexchange.model.dto.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ali Göktaş on 12,August,2020
 */

@Parcelize
data class CoinDetailDTO(

	@SerializedName("d")
	val items: List<DItem>

) : Parcelable





























