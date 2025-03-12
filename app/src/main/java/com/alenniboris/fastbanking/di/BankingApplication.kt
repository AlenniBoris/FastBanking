package com.alenniboris.fastbanking.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

val myModules = listOf<Module>(
    DispatchersModule,
    UserModule
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