package com.coursework.featureSearchBooks.di

import com.coursework.featureSearchBooks.presentation.SearchBooksViewModel
import com.coursework.featureSearchBooks.presentation.mapper.BookViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureSearchBooksModule = module {
    viewModelOf(::SearchBooksViewModel)

    factoryOf(::BookViewStateMapper)
}