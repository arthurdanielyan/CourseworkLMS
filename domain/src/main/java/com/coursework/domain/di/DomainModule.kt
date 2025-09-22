package com.coursework.domain.di

import com.coursework.domain.usecases.DownloadPdfUseCase
import com.coursework.domain.usecases.GetBookDetailsUseCase
import com.coursework.domain.usecases.GetUserTypeUseCase
import com.coursework.domain.usecases.SearchBooksUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::SearchBooksUseCase)
    factoryOf(::GetUserTypeUseCase)
    factoryOf(::GetBookDetailsUseCase)
    factoryOf(::DownloadPdfUseCase)
}