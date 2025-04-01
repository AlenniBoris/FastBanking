package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.HelpRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import org.koin.dsl.module

val HelpModule = module {

    single<IHelpRepository> {
        HelpRepositoryImpl()
    }
}