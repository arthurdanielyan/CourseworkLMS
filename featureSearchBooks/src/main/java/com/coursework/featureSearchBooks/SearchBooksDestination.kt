package com.coursework.featureSearchBooks

import com.coursework.corePresentation.Destination
import kotlinx.serialization.Serializable

@Serializable
object SearchBooksDestination : Destination

@Serializable
object BooksListDestination : Destination

@Serializable
object BookSearchFiltersDestination : Destination
