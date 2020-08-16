package com.aligkts.cryptoexchange.model.dto.response


import com.google.gson.annotations.SerializedName

data class GraphItem(
    @SerializedName("c")
    val price: Double,
    @SerializedName("d")
    val time: Long
)