package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.CheckVerificationCodeForRegistrationUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.GetBankLocationsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.SendVerificationCodeForRegistrationUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.SignOutUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.logic.ICheckVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ISendVerificationCodeForRegistrationUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ISignOutUseCase
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
}