package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.usecase.logic.ICheckVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ISendVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.AtmMapNotRegisteredUserScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.register.registration_as_app_client.RegistrationAsAppClientScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.register.registration_options.RegistrationOptionsScreenViewModel
import org.koin.android.ext.koin.androidApplication
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

    viewModel<RegistrationOptionsScreenViewModel> {
        RegistrationOptionsScreenViewModel()
    }

    viewModel<RegistrationAsAppClientScreenViewModel> {
        RegistrationAsAppClientScreenViewModel(
            sendVerificationCodeForRegistrationToNumberUseCase = get<ISendVerificationCodeForRegistrationUseCase>(),
            checkVerificationCodeForRegistrationUseCase = get<ICheckVerificationCodeForRegistrationUseCase>(),
            registerUserIntoBankingUseCase = get<IRegisterUserIntoBankingUseCase>()
        )
    }

    viewModel<AtmMapNotRegisteredUserScreenViewModel> {
        AtmMapNotRegisteredUserScreenViewModel(
            apl = androidApplication(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>()
        )
    }
}