package com.coursework.data.di

import com.coursework.data.bookDetails.DummyBookMetadataRepository
import com.coursework.data.bookDetails.mapper.NamedItemResponseMapper
import com.coursework.data.books.DummyBooksRepository
import com.coursework.data.downloader.AndroidDownloader
import com.coursework.data.downloader.Downloader
import com.coursework.domain.bookDetails.BookMetadataRepository
import com.coursework.domain.books.BooksRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single<BooksRepository> {
        DummyBooksRepository(
            downloader = get(),
        )
    }

    single<BookMetadataRepository> {
        DummyBookMetadataRepository(
            namedItemResponseMapper = get(),
        )
    }

    factory<Downloader> {
        AndroidDownloader(
            context = get(),
        )
    }

    factoryOf(::NamedItemResponseMapper)
}