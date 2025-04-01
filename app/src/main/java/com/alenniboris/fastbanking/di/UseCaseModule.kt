package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllCurrenciesInfoUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllExchangeRatesForCurrencyUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetCurrenciesExchangeRateUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.CallPhoneNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.OpenMessengerUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.map.GetBankLocationsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.CheckVerificationCodeForRegistrationUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.SendVerificationCodeForRegistrationUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.SignOutUseCaseImpl
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
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISignOutUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import org.koin.dsl.module

val UseCaseModule = module {

    single<IGetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ILoginUserIntoBankingUseCase> {
        LoginUserIntoBankingUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IRegisterUserIntoBankingUseCase> {
        RegisterUserIntoBankingUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISignOutUseCase> {
        SignOutUseCaseImpl(
            userRepository = get<IUserRepository>()
        )
    }

    factory<ISendVerificationCodeForRegistrationUseCase> {
        SendVerificationCodeForRegistrationUseCaseImpl(
            userRepository = get<IUserRepository>()
        )
    }

    factory<ICheckVerificationCodeForRegistrationUseCase> {
        CheckVerificationCodeForRegistrationUseCaseImpl(
            userRepository = get<IUserRepository>()
        )
    }

    factory<IGetBankLocationsUseCase> {
        GetBankLocationsUseCaseImpl(
            mapsRepository = get<IMapsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllCurrenciesInfoUseCase> {
        GetAllCurrenciesInfoUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCurrenciesExchangeRateUseCase> {
        GetCurrenciesExchangeRateUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllExchangeRatesForCurrencyUseCase> {
        GetAllExchangeRatesForCurrencyUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ICallPhoneNumberUseCase> {
        CallPhoneNumberUseCaseImpl(
            helpRepository = get<IHelpRepository>()
        )
    }

    factory<IOpenMessengerUseCase> {
        OpenMessengerUseCaseImpl(
            helpRepository = get<IHelpRepository>()
        )
    }
}