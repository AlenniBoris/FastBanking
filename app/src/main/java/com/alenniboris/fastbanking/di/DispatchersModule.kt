package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val DispatchersModule = module {

    single<IAppDispatchers> {
        object : IAppDispatchers {
            override val Main: CoroutineDispatcher = Dispatchers.Main
            override val IO: CoroutineDispatcher = Dispatchers.IO
            override val Default: CoroutineDispatcher = Dispatchers.Default
        }
    }

}