package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.MapsRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import org.koin.dsl.module

val MapsModule = module {

    single<IMapsRepository>{
        MapsRepositoryImpl()
    }
}