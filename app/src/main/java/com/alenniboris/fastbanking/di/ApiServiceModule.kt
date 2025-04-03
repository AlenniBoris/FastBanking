package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val ApiServiceModule = module {

    single<IExchangeRateApiService> {
        IExchangeRateApiService.create(
            apl = androidApplication()
        )
    }
}