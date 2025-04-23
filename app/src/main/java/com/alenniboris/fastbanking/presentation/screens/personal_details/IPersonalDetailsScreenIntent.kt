package com.alenniboris.fastbanking.presentation.screens.personal_details

sealed interface IPersonalDetailsScreenIntent {

    data object NavigateBack : IPersonalDetailsScreenIntent
}