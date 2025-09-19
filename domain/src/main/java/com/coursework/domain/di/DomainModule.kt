package com.coursework.domain.di

import com.coursework.domain.SearchBooksUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::SearchBooksUseCase)
}