package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.CurrencyRepositoryImpl
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val CurrencyModule = module {

    single<ICurrencyRepository> {
        CurrencyRepositoryImpl(
            apiService = get<IExchangeRateApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IExchangeRateApiService> {
        IExchangeRateApiService.create(
            apl = androidApplication()
        )
    }
}