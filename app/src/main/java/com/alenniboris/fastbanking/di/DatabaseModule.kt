package com.alenniboris.fastbanking.di

import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val DatabaseModule = module {

    single<FirebaseDatabase> {
        FirebaseDatabase.getInstance()
    }
}