package com.aligkts.cryptoexchange.model.dto.response


import android.os.Parcelable
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.RecyclerViewItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinItemDTO(
    @SerializedName("buy")
    val buy: String,
    @SerializedName("clo")
    val hour: String,
    @SerializedName("cod")
    val code: String,
    @SerializedName("ddi")
    val diff: String,
    @SerializedName("def")
    val description: String,
    @SerializedName("hig")
    val high: String,
    @SerializedName("las")
    val last: String,
    @SerializedName("low")
    val low: String,
    @SerializedName("pdc")
    val closing: String,
    @SerializedName("pdd")
    val diffPercentage: String,
    @SerializedName("rtp")
    val instantData: Boolean,
    @SerializedName("sel")
    val sell: String,
    @SerializedName("tke")
    val id: String
) : RecyclerViewItem(R.layout.item_coin), Parcelable