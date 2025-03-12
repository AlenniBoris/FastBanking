package com.alenniboris.fastbanking.domain.utils

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {
    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
}