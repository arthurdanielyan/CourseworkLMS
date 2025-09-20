package com.coursework.featureBookDetails.di

import com.coursework.featureBookDetails.presentation.BookDetailsViewModel
import com.coursework.featureBookDetails.presentation.mapper.BookViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureBookDetailsModule = module {
    viewModelOf(::BookDetailsViewModel)

    factoryOf(::BookViewStateMapper)
}