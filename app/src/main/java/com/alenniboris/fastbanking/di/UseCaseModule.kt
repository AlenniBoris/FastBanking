package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetApplicationInfoUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetBankNewsByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetBankNewsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.cards.GetAllUserCardsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllCurrenciesInfoUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllExchangeRatesForCurrencyUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetCurrenciesExchangeRateUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.CallPhoneNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.OpenMessengerUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.map.GetBankLocationsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.GetAllUserTransactionsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.ChangePasswordUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.CheckVerificationCodeUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.GetUserByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.SendVerificationCodeUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.SignOutUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetApplicationInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IChangePasswordUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetUserByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISignOutUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import org.koin.dsl.module

val UseCaseModule = module {

    single<IGetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ILoginUserIntoBankingUseCase> {
        LoginUserIntoBankingUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IRegisterUserIntoBankingUseCase> {
        RegisterUserIntoBankingUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISignOutUseCase> {
        SignOutUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
        )
    }


    factory<ISendVerificationCodeUseCase> {
        SendVerificationCodeUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
        )
    }

    factory<ICheckVerificationCodeUseCase> {
        CheckVerificationCodeUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
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

    factory<IGetApplicationInfoUseCase> {
        GetApplicationInfoUseCaseImpl(
            bankInfoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetBankNewsUseCase> {
        GetBankNewsUseCaseImpl(
            infoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetBankNewsByIdUseCase> {
        GetBankNewsByIdUseCaseImpl(
            infoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetUserByIdUseCase> {
        GetUserByIdUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IChangePasswordUseCase> {
        ChangePasswordUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserCardsUseCase> {
        GetAllUserCardsUseCaseImpl(
            userRepository = get<IUserRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserTransactionsUseCase> {
        GetAllUserTransactionsUseCaseImpl(
            userRepository = get<IUserRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }
}