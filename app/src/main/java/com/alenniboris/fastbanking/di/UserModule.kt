package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.UserRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.user.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.user.SignOutUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISignOutUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val UserModule = module {

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }


    single<IUserRepository> {
        UserRepositoryImpl(
            apl = androidApplication(),
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

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

}