package com.coursework.featureEditBook.di

import com.coursework.featureEditBook.presentation.EditBookViewModel
import com.coursework.featureEditBook.presentation.mapper.EditBookViewStateMapper
import com.coursework.featureEditBook.presentation.mapper.FilterViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureEditBookModule = module {
    viewModelOf(::EditBookViewModel)

    factoryOf(::EditBookViewStateMapper)
    factoryOf(::FilterViewStateMapper)
}