package com.alenniboris.fastbanking.presentation.screens.additions

sealed interface IAdditionsScreenIntent {

    data class ProceedAccordingToAction(val action: AdditionsCategoriesAction) :
        IAdditionsScreenIntent

    data object UpdatePraiseDialogVisibility : IAdditionsScreenIntent

    data class ChangePraiseText(val text: String) : IAdditionsScreenIntent

    data object SendUserPraise : IAdditionsScreenIntent
}