package com.alenniboris.fastbanking.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val AuthModule = module {

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}