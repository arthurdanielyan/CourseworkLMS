package com.coursework.featureSearchBooks.searchFilters.di

import com.coursework.featureSearchBooks.searchFilters.SearchFiltersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchFiltersModule = module {
    viewModelOf(::SearchFiltersViewModel)
}