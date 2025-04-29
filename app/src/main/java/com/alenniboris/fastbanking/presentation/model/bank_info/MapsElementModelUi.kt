package com.alenniboris.fastbanking.presentation.model.bank_info

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapsElementModelUi(
    private val domainModel: MapsElementModelDomain,
    val coordinates: LatLng = LatLng(domainModel.latitude, domainModel.longitude),
    val addressString: String = domainModel.address,
    val type: MapElementType = domainModel.type,
    val workingTime: String = domainModel.workingTime
) : ClusterItem {

    override fun getPosition(): LatLng = coordinates

    override fun getTitle(): String = type.name

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null
}

fun MapElementType.toTypeTitle(): Int = when (this) {
    MapElementType.OFFICE -> R.string.office_text
    MapElementType.ATM -> R.string.atm_text
    MapElementType.OFFICE_WITH_ATM -> R.string.office_with_atm_text
    MapElementType.UNDEFINED -> R.string.undefined_text
}

fun MapElementType.toTypeIcon(): Int = when (this) {
    MapElementType.OFFICE -> R.drawable.office_type_icon
    MapElementType.ATM -> R.drawable.atm_locale_icon
    MapElementType.OFFICE_WITH_ATM -> R.drawable.office_type_icon
    MapElementType.UNDEFINED -> R.drawable.undefined_type_icon
}