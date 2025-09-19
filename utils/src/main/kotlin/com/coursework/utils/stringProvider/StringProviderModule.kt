package com.coursework.utils.stringProvider

import org.koin.dsl.module

val stringProviderModule = module {
    single<StringProvider> {
        StringProviderImpl(
            applicationContext = get()
        )
    }
}