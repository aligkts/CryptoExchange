package com.aligkts.cryptoexchange.model.dto.response


import com.google.gson.annotations.SerializedName

/**
 * Created by Ali Göktaş on 12,August,2020
 */

data class GraphItem(
    @SerializedName("c")
    val price: Double,
    @SerializedName("d")
    val time: Long
)