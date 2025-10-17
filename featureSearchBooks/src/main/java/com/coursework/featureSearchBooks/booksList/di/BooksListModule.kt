package com.coursework.featureSearchBooks.booksList.di

import com.coursework.featureSearchBooks.booksList.BooksListViewModel
import com.coursework.featureSearchBooks.booksList.BooksPagingSource
import com.coursework.featureSearchBooks.booksList.mapper.BookViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val booksListModule = module {
    viewModelOf(::BooksListViewModel)

    factoryOf(::BookViewStateMapper)
    factoryOf(BooksPagingSource::Factory)
}