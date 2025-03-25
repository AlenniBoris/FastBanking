package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.CurrencyRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import org.koin.dsl.module

val CurrencyModule = module {

    single<ICurrencyRepository> {
        CurrencyRepositoryImpl()
    }
}