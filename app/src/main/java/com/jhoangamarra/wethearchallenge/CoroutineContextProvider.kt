package com.jhoangamarra.wethearchallenge

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val io: CoroutineContext
    val computation: CoroutineContext
    val main: CoroutineContext
}