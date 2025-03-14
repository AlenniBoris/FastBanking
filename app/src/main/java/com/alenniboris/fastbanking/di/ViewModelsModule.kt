package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelsModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>()
        )
    }

    viewModel<LoginScreenViewModel> {
        LoginScreenViewModel(
            loginUserIntoBankingUseCase = get<ILoginUserIntoBankingUseCase>()
        )
    }
}