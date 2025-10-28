package com.coursework.corePresentation.viewState.books.di

import com.coursework.corePresentation.viewState.books.mapper.BookViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreBooksModule = module {
    factoryOf(::BookViewStateMapper)
}