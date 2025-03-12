package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.UserRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import org.koin.dsl.module

val UserModule = module {

    single<IUserRepository> {
        UserRepositoryImpl()
    }

    single<IGetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<ILoginUserIntoBankingUseCase> {
        LoginUserIntoBankingUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IRegisterUserIntoBankingUseCase> {
        RegisterUserIntoBankingUseCaseImpl(
            userRepository = get<IUserRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}