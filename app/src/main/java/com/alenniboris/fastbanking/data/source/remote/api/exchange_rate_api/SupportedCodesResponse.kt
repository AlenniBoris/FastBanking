package com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api


import com.google.gson.annotations.SerializedName

data class SupportedCodesResponse(
    @SerializedName("documentation")
    val documentation: String?,
    @SerializedName("error-type")
    val errorType: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("supported_codes")
    val supportedCodes: List<List<String?>?>?,
    @SerializedName("terms_of_use")
    val termsOfUse: String?
) {

    val hasSomeValueMissing: Boolean = documentation == null
            || result == null
            || supportedCodes == null
            || termsOfUse == null
}