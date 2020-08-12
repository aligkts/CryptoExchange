package com.aligkts.cryptoexchange.model.dto.response


import android.os.Parcelable
import com.aligkts.cryptoexchange.extension.getCoinSpinnerSelectedIndex
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository
import com.aligkts.cryptoexchange.util.Constant
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
) : Parcelable {

    fun getFirstSymbolData(): String {
        return getRelatedFieldValue(Constant.FIRST_SYMBOL)
    }

    fun getSecondSymbolData(): String {
        return getRelatedFieldValue(Constant.SECOND_SYMBOL)
    }

    fun getRelatedFieldValue(key: String): String {
        return when (GenericSecureRepository.default.getString(key)?.getCoinSpinnerSelectedIndex()) {
            0 -> {
                buy
            }
            1 -> {
                diff
            }
            2 -> {
                high
            }
            3 -> {
                last
            }
            4 -> {
                low
            }
            5 -> {
                closing
            }
            6 -> {
                diff
            }
            else -> {
                sell
            }
        }
    }
}