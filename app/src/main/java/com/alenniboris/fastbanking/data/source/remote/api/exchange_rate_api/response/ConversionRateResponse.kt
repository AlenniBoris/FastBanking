package com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response


import com.google.gson.annotations.SerializedName

data class ConversionRateResponse(
    @SerializedName("base_code")
    val baseCode: String?,
    @SerializedName("conversion_rate")
    val conversionRate: String?,
    @SerializedName("documentation")
    val documentation: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("target_code")
    val targetCode: String?,
    @SerializedName("terms_of_use")
    val termsOfUse: String?,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: String?,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String?,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: String?,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String?,
    @SerializedName("error-type")
    val errorType: String?
) {

    val hasSomeValueMissing: Boolean = baseCode == null
            || conversionRate == null
            || documentation == null
            || result == null
            || targetCode == null
            || termsOfUse == null
            || timeLastUpdateUnix == null
            || timeLastUpdateUtc == null
            || timeNextUpdateUnix == null
            || timeNextUpdateUtc == null
}