package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.data.repository.MapsRepositoryImpl
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val MapsModule = module {

    single<IMapsRepository>{
        MapsRepositoryImpl(
            database = get<FirebaseDatabase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }
}