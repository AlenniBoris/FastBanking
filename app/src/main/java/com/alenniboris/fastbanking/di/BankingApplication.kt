package com.alenniboris.fastbanking.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

val myModules = listOf(
    DispatchersModule,
    UserModule,
    DatabaseModule,
    ViewModelsModule,
    UseCaseModule,
    MapsModule,
    CurrencyModule,
    CountriesModule
)

class BankingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BankingApplication)
            modules(myModules)
        }
    }
}