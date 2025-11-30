package com.omarkarimli.morty.core.di

import com.omarkarimli.network.KtorClient
import org.koin.dsl.module

val networkModule = module {
    single { KtorClient() }
}