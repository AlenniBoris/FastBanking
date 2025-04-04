package com.alenniboris.fastbanking.presentation.screens.news_details

sealed interface INewsDetailsScreenIntent {

    data object NavigateBack : INewsDetailsScreenIntent
}