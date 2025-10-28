package com.coursework.domain.di

import com.coursework.domain.bookDetails.usecases.GetBookDetailsUseCase
import com.coursework.domain.bookDetails.usecases.GetCategories
import com.coursework.domain.bookDetails.usecases.GetLanguages
import com.coursework.domain.bookDetails.usecases.GetMaxReservationDate
import com.coursework.domain.bookDetails.usecases.GetTeachers
import com.coursework.domain.bookDetails.usecases.ReserveBookUseCase
import com.coursework.domain.books.usecases.DownloadPdfUseCase
import com.coursework.domain.books.usecases.GetBooksUseCase
import com.coursework.domain.user.usecases.GetUserTypeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetBooksUseCase)
    factoryOf(::GetUserTypeUseCase)
    factoryOf(::GetBookDetailsUseCase)
    factoryOf(::DownloadPdfUseCase)
    factoryOf(::GetCategories)
    factoryOf(::GetLanguages)
    factoryOf(::GetTeachers)
    factoryOf(::ReserveBookUseCase)
    factoryOf(::GetMaxReservationDate)
}