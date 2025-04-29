package com.alenniboris.fastbanking.presentation.model.appliance

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.IProductAppliance
import com.alenniboris.fastbanking.presentation.mappers.toUiString
import java.text.SimpleDateFormat
import java.util.Locale

data class ProductApplianceModelUi(
    val domainModel: IProductAppliance
) {

    val dateText: String =
        SimpleDateFormat(
            "dd-MM-yyyy, HH:mm",
            Locale.getDefault()
        ).format(domainModel.dateOfAppliance)

    val statusImageId: Int =
        when (domainModel.status) {
            ApplianceStatus.Approved -> R.drawable.appliance_approved_status_image
            ApplianceStatus.Waiting -> R.drawable.appliance_waiting_status_image
            ApplianceStatus.Declined -> R.drawable.appliance_declined_status_image
            ApplianceStatus.Undefined -> R.drawable.appliance_undefined_status_image
        }

    val statusIconId: Int =
        when (domainModel.status) {
            ApplianceStatus.Approved -> R.drawable.approved_status_icon
            ApplianceStatus.Waiting -> R.drawable.waiting_status_icon
            ApplianceStatus.Declined -> R.drawable.declined_status_icon
            ApplianceStatus.Undefined -> R.drawable.undefined_status_icon
        }

    val officeLocationText = """
        ${domainModel.selectedOffice.address}
        ${domainModel.selectedOffice.workingTime}
    """.trimIndent()

    val currencyText = domainModel.currencyCode

    val statusTextId: Int =
        when (domainModel.status) {
            ApplianceStatus.Approved -> R.string.approved_status_text
            ApplianceStatus.Waiting -> R.string.waiting_status_text
            ApplianceStatus.Declined -> R.string.declined_status_text
            ApplianceStatus.Undefined -> R.string.undefined_status_text
        }

    val applianceTextString: Int =
        when (domainModel) {
            is CardApplianceModelDomain -> {
                (domainModel as? CardApplianceModelDomain)?.detailedCardApplianceType?.toUiString()
                    ?: R.string.undefined_text
            }

            is CreditApplianceModelDomain -> {
                (domainModel as? CreditApplianceModelDomain)?.detailedCreditApplianceType?.toUiString()
                    ?: R.string.undefined_text
            }

            is DepositApplianceModelDomain -> {
                (domainModel as? DepositApplianceModelDomain)?.detailedDepositApplianceType?.toUiString()
                    ?: R.string.undefined_text
            }

            else -> R.string.undefined_text
        }
}

fun IProductAppliance.toModelUi(): ProductApplianceModelUi =
    ProductApplianceModelUi(
        domainModel = this
    )