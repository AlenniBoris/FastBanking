package com.alenniboris.fastbanking.domain.model.appliances

enum class CardDetailedApplianceType {
    ISSUE_PAYMENT_CARD,
    ISSUE_VIRTUAL_CARD,
    REISSUE_PAYMENT_CARD,
    REISSUE_VIRTUAL_CARD,
    UNDEFINED
}

enum class CreditDetailedApplianceType {
    BBANK_CREDIT,
    SOCIAL_CREDIT,
    UNDEFINED
}

enum class DepositDetailedApplianceType {
    URGENT_DEPOSIT,
    UNDEFINED
}