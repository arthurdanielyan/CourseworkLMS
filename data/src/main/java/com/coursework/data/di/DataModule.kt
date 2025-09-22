package com.coursework.data.di

import com.coursework.data.DummyBooksRepository
import com.coursework.data.downloader.AndroidDownloader
import com.coursework.data.downloader.Downloader
import com.coursework.domain.repository.BooksRepository
import org.koin.dsl.module

val dataModule = module {
    single<BooksRepository> {
        DummyBooksRepository(
            downloader = get(),
        )
    }

    factory<Downloader> {
        AndroidDownloader(
            context = get(),
        )
    }
}