package com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response


import com.google.gson.annotations.SerializedName

data class LatestCurrencyRatesResponse(
    @SerializedName("base_code")
    val baseCode: String?,
    @SerializedName("conversion_rates")
    val conversionRates: ConversionRates?,
    @SerializedName("documentation")
    val documentation: String?,
    @SerializedName("result")
    val result: String?,
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
    val errorType: String?,
) {
    data class ConversionRates(
        @SerializedName("AED")
        val aED: String?,
        @SerializedName("AFN")
        val aFN: String?,
        @SerializedName("ALL")
        val aLL: String?,
        @SerializedName("AMD")
        val aMD: String?,
        @SerializedName("ANG")
        val aNG: String?,
        @SerializedName("AOA")
        val aOA: String?,
        @SerializedName("ARS")
        val aRS: String?,
        @SerializedName("AUD")
        val aUD: String?,
        @SerializedName("AWG")
        val aWG: String?,
        @SerializedName("AZN")
        val aZN: String?,
        @SerializedName("BAM")
        val bAM: String?,
        @SerializedName("BBD")
        val bBD: String?,
        @SerializedName("BDT")
        val bDT: String?,
        @SerializedName("BGN")
        val bGN: String?,
        @SerializedName("BHD")
        val bHD: String?,
        @SerializedName("BIF")
        val bIF: String?,
        @SerializedName("BMD")
        val bMD: String?,
        @SerializedName("BND")
        val bND: String?,
        @SerializedName("BOB")
        val bOB: String?,
        @SerializedName("BRL")
        val bRL: String?,
        @SerializedName("BSD")
        val bSD: String?,
        @SerializedName("BTN")
        val bTN: String?,
        @SerializedName("BWP")
        val bWP: String?,
        @SerializedName("BYN")
        val bYN: String?,
        @SerializedName("BZD")
        val bZD: String?,
        @SerializedName("CAD")
        val cAD: String?,
        @SerializedName("CDF")
        val cDF: String?,
        @SerializedName("CHF")
        val cHF: String?,
        @SerializedName("CLP")
        val cLP: String?,
        @SerializedName("CNY")
        val cNY: String?,
        @SerializedName("COP")
        val cOP: String?,
        @SerializedName("CRC")
        val cRC: String?,
        @SerializedName("CUP")
        val cUP: String?,
        @SerializedName("CVE")
        val cVE: String?,
        @SerializedName("CZK")
        val cZK: String?,
        @SerializedName("DJF")
        val dJF: String?,
        @SerializedName("DKK")
        val dKK: String?,
        @SerializedName("DOP")
        val dOP: String?,
        @SerializedName("DZD")
        val dZD: String?,
        @SerializedName("EGP")
        val eGP: String?,
        @SerializedName("ERN")
        val eRN: String?,
        @SerializedName("ETB")
        val eTB: String?,
        @SerializedName("EUR")
        val eUR: String?,
        @SerializedName("FJD")
        val fJD: String?,
        @SerializedName("FKP")
        val fKP: String?,
        @SerializedName("FOK")
        val fOK: String?,
        @SerializedName("GBP")
        val gBP: String?,
        @SerializedName("GEL")
        val gEL: String?,
        @SerializedName("GGP")
        val gGP: String?,
        @SerializedName("GHS")
        val gHS: String?,
        @SerializedName("GIP")
        val gIP: String?,
        @SerializedName("GMD")
        val gMD: String?,
        @SerializedName("GNF")
        val gNF: String?,
        @SerializedName("GTQ")
        val gTQ: String?,
        @SerializedName("GYD")
        val gYD: String?,
        @SerializedName("HKD")
        val hKD: String?,
        @SerializedName("HNL")
        val hNL: String?,
        @SerializedName("HRK")
        val hRK: String?,
        @SerializedName("HTG")
        val hTG: String?,
        @SerializedName("HUF")
        val hUF: String?,
        @SerializedName("IDR")
        val iDR: String?,
        @SerializedName("ILS")
        val iLS: String?,
        @SerializedName("IMP")
        val iMP: String?,
        @SerializedName("INR")
        val iNR: String?,
        @SerializedName("IQD")
        val iQD: String?,
        @SerializedName("IRR")
        val iRR: String?,
        @SerializedName("ISK")
        val iSK: String?,
        @SerializedName("JEP")
        val jEP: String?,
        @SerializedName("JMD")
        val jMD: String?,
        @SerializedName("JOD")
        val jOD: String?,
        @SerializedName("JPY")
        val jPY: String?,
        @SerializedName("KES")
        val kES: String?,
        @SerializedName("KGS")
        val kGS: String?,
        @SerializedName("KHR")
        val kHR: String?,
        @SerializedName("KID")
        val kID: String?,
        @SerializedName("KMF")
        val kMF: String?,
        @SerializedName("KRW")
        val kRW: String?,
        @SerializedName("KWD")
        val kWD: String?,
        @SerializedName("KYD")
        val kYD: String?,
        @SerializedName("KZT")
        val kZT: String?,
        @SerializedName("LAK")
        val lAK: String?,
        @SerializedName("LBP")
        val lBP: String?,
        @SerializedName("LKR")
        val lKR: String?,
        @SerializedName("LRD")
        val lRD: String?,
        @SerializedName("LSL")
        val lSL: String?,
        @SerializedName("LYD")
        val lYD: String?,
        @SerializedName("MAD")
        val mAD: String?,
        @SerializedName("MDL")
        val mDL: String?,
        @SerializedName("MGA")
        val mGA: String?,
        @SerializedName("MKD")
        val mKD: String?,
        @SerializedName("MMK")
        val mMK: String?,
        @SerializedName("MNT")
        val mNT: String?,
        @SerializedName("MOP")
        val mOP: String?,
        @SerializedName("MRU")
        val mRU: String?,
        @SerializedName("MUR")
        val mUR: String?,
        @SerializedName("MVR")
        val mVR: String?,
        @SerializedName("MWK")
        val mWK: String?,
        @SerializedName("MXN")
        val mXN: String?,
        @SerializedName("MYR")
        val mYR: String?,
        @SerializedName("MZN")
        val mZN: String?,
        @SerializedName("NAD")
        val nAD: String?,
        @SerializedName("NGN")
        val nGN: String?,
        @SerializedName("NIO")
        val nIO: String?,
        @SerializedName("NOK")
        val nOK: String?,
        @SerializedName("NPR")
        val nPR: String?,
        @SerializedName("NZD")
        val nZD: String?,
        @SerializedName("OMR")
        val oMR: String?,
        @SerializedName("PAB")
        val pAB: String?,
        @SerializedName("PEN")
        val pEN: String?,
        @SerializedName("PGK")
        val pGK: String?,
        @SerializedName("PHP")
        val pHP: String?,
        @SerializedName("PKR")
        val pKR: String?,
        @SerializedName("PLN")
        val pLN: String?,
        @SerializedName("PYG")
        val pYG: String?,
        @SerializedName("QAR")
        val qAR: String?,
        @SerializedName("RON")
        val rON: String?,
        @SerializedName("RSD")
        val rSD: String?,
        @SerializedName("RUB")
        val rUB: String?,
        @SerializedName("RWF")
        val rWF: String?,
        @SerializedName("SAR")
        val sAR: String?,
        @SerializedName("SBD")
        val sBD: String?,
        @SerializedName("SCR")
        val sCR: String?,
        @SerializedName("SDG")
        val sDG: String?,
        @SerializedName("SEK")
        val sEK: String?,
        @SerializedName("SGD")
        val sGD: String?,
        @SerializedName("SHP")
        val sHP: String?,
        @SerializedName("SLE")
        val sLE: String?,
        @SerializedName("SLL")
        val sLL: String?,
        @SerializedName("SOS")
        val sOS: String?,
        @SerializedName("SRD")
        val sRD: String?,
        @SerializedName("SSP")
        val sSP: String?,
        @SerializedName("STN")
        val sTN: String?,
        @SerializedName("SYP")
        val sYP: String?,
        @SerializedName("SZL")
        val sZL: String?,
        @SerializedName("THB")
        val tHB: String?,
        @SerializedName("TJS")
        val tJS: String?,
        @SerializedName("TMT")
        val tMT: String?,
        @SerializedName("TND")
        val tND: String?,
        @SerializedName("TOP")
        val tOP: String?,
        @SerializedName("TRY")
        val tRY: String?,
        @SerializedName("TTD")
        val tTD: String?,
        @SerializedName("TVD")
        val tVD: String?,
        @SerializedName("TWD")
        val tWD: String?,
        @SerializedName("TZS")
        val tZS: String?,
        @SerializedName("UAH")
        val uAH: String?,
        @SerializedName("UGX")
        val uGX: String?,
        @SerializedName("USD")
        val uSD: String?,
        @SerializedName("UYU")
        val uYU: String?,
        @SerializedName("UZS")
        val uZS: String?,
        @SerializedName("VES")
        val vES: String?,
        @SerializedName("VND")
        val vND: String?,
        @SerializedName("VUV")
        val vUV: String?,
        @SerializedName("WST")
        val wST: String?,
        @SerializedName("XAF")
        val xAF: String?,
        @SerializedName("XCD")
        val xCD: String?,
        @SerializedName("XCG")
        val xCG: String?,
        @SerializedName("XDR")
        val xDR: String?,
        @SerializedName("XOF")
        val xOF: String?,
        @SerializedName("XPF")
        val xPF: String?,
        @SerializedName("YER")
        val yER: String?,
        @SerializedName("ZAR")
        val zAR: String?,
        @SerializedName("ZMW")
        val zMW: String?,
        @SerializedName("ZWL")
        val zWL: String?
    )

    val hasSomeValueMissing: Boolean = baseCode == null
            || conversionRates == null
            || documentation == null
            || result == null
            || termsOfUse == null
            || timeLastUpdateUnix == null
            || timeLastUpdateUtc == null
            || timeNextUpdateUnix == null
            || timeNextUpdateUtc == null
}