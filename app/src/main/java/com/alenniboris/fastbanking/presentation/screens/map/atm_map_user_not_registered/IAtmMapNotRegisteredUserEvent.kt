package com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered

sealed interface IAtmMapNotRegisteredUserEvent {

    data class ShowToastMessage(val messageId: Int) : IAtmMapNotRegisteredUserEvent
}