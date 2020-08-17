package com.aligkts.cryptoexchange.model.dto.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ali Göktaş on 12,August,2020
 */

@Parcelize
data class CoinResponse(
    @SerializedName("l")
    val coins: ArrayList<CoinItemDTO>,
    @SerializedName("z")
    val z: String
): Parcelable