package com.coursework.featureSearchBooks.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchBooksSharedViewModel : ViewModel() {

    private val _searchFilters = MutableStateFlow(SearchFilters())
    val searchFilters = _searchFilters.asStateFlow()

    fun setResult(filters: SearchFilters) {
        _searchFilters.update { filters }
    }
}