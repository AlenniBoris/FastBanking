package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.currency.CurrencyScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.help.HelpScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.map.AtmMapScreenViewModel
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

    viewModel<AtmMapScreenViewModel> {
        AtmMapScreenViewModel(
            apl = androidApplication(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>()
        )
    }

    viewModel<CurrencyScreenViewModel> {
        CurrencyScreenViewModel(
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            getAllCurrenciesInfoUseCase = get<IGetAllCurrenciesInfoUseCase>(),
            getAllExchangeRatesForCurrencyUseCase = get<IGetAllExchangeRatesForCurrencyUseCase>()
        )
    }

    viewModel<HelpScreenViewModel> {
        HelpScreenViewModel(
            apl = androidApplication(),
            callPhoneNumberUseCase = get<ICallPhoneNumberUseCase>(),
            openMessengerUseCase = get<IOpenMessengerUseCase>()
        )
    }

    viewModel<AdditionsScreenViewModel>{
        AdditionsScreenViewModel()
    }
}