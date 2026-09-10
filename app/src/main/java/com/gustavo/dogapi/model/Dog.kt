package com.gustavo.dogapi.model

import com.google.gson.annotations.SerializedName

data class DogDetailResponse(
    @SerializedName("message") val photoUrl: String,
    @SerializedName("status") val status: String,
    var breedName: String = ""
)
