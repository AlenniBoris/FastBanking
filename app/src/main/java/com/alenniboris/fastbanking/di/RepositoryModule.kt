package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.AuthenticationRepositoryImpl
import com.alenniboris.fastbanking.data.repository.BankInfoRepositoryImpl
import com.alenniboris.fastbanking.data.repository.CurrencyRepositoryImpl
import com.alenniboris.fastbanking.data.repository.HelpRepositoryImpl
import com.alenniboris.fastbanking.data.repository.MapsRepositoryImpl
import com.alenniboris.fastbanking.data.repository.UserRepositoryImpl
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val RepositoryModule = module {

    single<IAuthenticationRepository> {
        AuthenticationRepositoryImpl(
            apl = androidApplication(),
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IMapsRepository> {
        MapsRepositoryImpl(
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IHelpRepository> {
        HelpRepositoryImpl(
            apl = androidApplication()
        )
    }

    single<ICurrencyRepository> {
        CurrencyRepositoryImpl(
            apiService = get<IExchangeRateApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IBankInfoRepository> {
        BankInfoRepositoryImpl(
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IUserRepository> {
        UserRepositoryImpl(
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }
}