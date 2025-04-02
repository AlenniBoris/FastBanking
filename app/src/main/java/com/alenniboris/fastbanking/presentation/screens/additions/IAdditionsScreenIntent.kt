package com.alenniboris.fastbanking.presentation.screens.additions

sealed interface IAdditionsScreenIntent {

    data class ProceedAccordingToAction(val action: AdditionsCategoriesAction) :
        IAdditionsScreenIntent
}