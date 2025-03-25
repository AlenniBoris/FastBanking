package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.CountriesRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.ICountriesRepository
import org.koin.dsl.module

val CountriesModule = module {

    single<ICountriesRepository> {
        CountriesRepositoryImpl()
    }
}