package com.coursework.data

import com.coursework.domain.model.books.Book
import com.coursework.domain.model.books.BookDetails
import kotlin.random.Random

object MockData {

    val books = listOf(
        Book(
            id = 1L,
            title = "Kotlin Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Josh Skeen", "David Greenhalgh"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true
        ),
        Book(
            id = 2L,
            title = "Android Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Bill Phillips", "Chris Stewart", "Kristin Marsicano"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true
        ),
        Book(
            id = 3L,
            title = "Pro Android 12",
            subtitle = "Developing Modern Mobile Apps",
            authors = listOf("Dave MacLean", "Satya Komatineni", "Sanjay Singh"),
            publisher = "Apress",
            hasPdfVersion = false
        ),
        Book(
            id = 4L,
            title = "Android Jetpack Compose Essentials",
            subtitle = null,
            authors = listOf("Neil Smyth"),
            publisher = "TechPress",
            hasPdfVersion = true
        ),
        Book(
            id = 5L,
            title = "Programming Android",
            subtitle = "Kotlin Edition",
            authors = listOf("Brett McLaughlin"),
            publisher = "O'Reilly Media",
            hasPdfVersion = false
        ),
        Book(
            id = 6L,
            title = "Head First Android Development",
            subtitle = "A Brain-Friendly Guide",
            authors = listOf("Dawn Griffiths", "David Griffiths"),
            publisher = "O'Reilly Media",
            hasPdfVersion = true
        ),
        Book(
            id = 7L,
            title = "Effective Kotlin",
            subtitle = "Best Practices for Kotlin Development",
            authors = listOf("Marcin Moskala"),
            publisher = "Leanpub",
            hasPdfVersion = true
        ),
        Book(
            id = 8L,
            title = "Kotlin Coroutines by Tutorials",
            subtitle = null,
            authors = listOf("Raywenderlich Tutorial Team"),
            publisher = "Raywenderlich",
            hasPdfVersion = true
        ),
        Book(
            id = 9L,
            title = "Android Clean Code",
            subtitle = null,
            authors = listOf("Robert C. Martin", "Android Dev Team"),
            publisher = "Prentice Hall",
            hasPdfVersion = false
        ),
        Book(
            id = 10L,
            title = "Mastering Android Development",
            subtitle = null,
            authors = listOf("John Horton"),
            publisher = "Packt Publishing",
            hasPdfVersion = true
        )
    ) + (1..200).map {
        Book(
            id = it.toLong(),
            title = "Book $it",
            subtitle = "Subtitle $it".takeIf { Random.nextBoolean() },
            authors = listOf("Author $it"),
            publisher = "Publisher $it",
            hasPdfVersion = Random.nextBoolean(),
        )
    }

    val bookDetails = listOf(
        BookDetails(
            id = 1L,
            title = "Kotlin Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Josh Skeen", "David Greenhalgh"),
            publisher = "Big Nerd Ranch",
            publicationYear = 2018,
            edition = "2nd",
            categories = listOf("Programming", "Kotlin", "Mobile Development"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Kotlin Programming Guide (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 10,
            copiesAvailable = 6,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 2L,
            title = "Android Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Bill Phillips", "Chris Stewart", "Kristin Marsicano"),
            publisher = "Big Nerd Ranch",
            publicationYear = 2021,
            edition = "4th",
            categories = listOf("Programming", "Android"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Android Programming Guide (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 12,
            copiesAvailable = 4,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 3L,
            title = "Pro Android 12",
            subtitle = "Developing Modern Mobile Apps",
            authors = listOf("Dave MacLean", "Satya Komatineni", "Sanjay Singh"),
            publisher = "Apress",
            publicationYear = 2021,
            edition = "1st",
            categories = listOf("Programming", "Android"),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 8,
            copiesAvailable = 3,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 4L,
            title = "Android Jetpack Compose Essentials",
            subtitle = null,
            authors = listOf("Neil Smyth"),
            publisher = "TechPress",
            publicationYear = 2022,
            edition = "1st",
            categories = listOf("Programming", "Android", "Jetpack Compose"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Jetpack Compose Essentials (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 15,
            copiesAvailable = 9,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 5L,
            title = "Programming Android",
            subtitle = "Kotlin Edition",
            authors = listOf("Brett McLaughlin"),
            publisher = "O'Reilly Media",
            publicationYear = 2020,
            edition = "5th",
            categories = listOf("Programming", "Android", "Kotlin"),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 7,
            copiesAvailable = 2,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 6L,
            title = "Head First Android Development",
            subtitle = "A Brain-Friendly Guide",
            authors = listOf("Dawn Griffiths", "David Griffiths"),
            publisher = "O'Reilly Media",
            publicationYear = 2021,
            edition = "3rd",
            categories = listOf("Programming", "Android"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Head First Android Dev (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 11,
            copiesAvailable = 5,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 7L,
            title = "Effective Kotlin",
            subtitle = "Best Practices for Kotlin Development",
            authors = listOf("Marcin Moskala"),
            publisher = "Leanpub",
            publicationYear = 2019,
            edition = "1st",
            categories = listOf("Programming", "Kotlin"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Effective Kotlin (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 6,
            copiesAvailable = 1,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 8L,
            title = "Kotlin Coroutines by Tutorials",
            subtitle = null,
            authors = listOf("Raywenderlich Tutorial Team"),
            publisher = "Raywenderlich",
            publicationYear = 2020,
            edition = "1st",
            categories = listOf("Programming", "Kotlin", "Concurrency"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Kotlin Coroutines (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 14,
            copiesAvailable = 10,
            language = "English",
            isReferenceOnly = false
        ),
        BookDetails(
            id = 9L,
            title = "Android Clean Code",
            subtitle = null,
            authors = listOf("Robert C. Martin", "Android Dev Team"),
            publisher = "Prentice Hall",
            publicationYear = 2019,
            edition = "1st",
            categories = listOf("Programming", "Android", "Software Engineering"),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 9,
            copiesAvailable = 3,
            language = "English",
            isReferenceOnly = true
        ),
        BookDetails(
            id = 10L,
            title = "Mastering Android Development",
            subtitle = null,
            authors = listOf("John Horton"),
            publisher = "Packt Publishing",
            publicationYear = 2022,
            edition = "2nd",
            categories = listOf("Programming", "Android"),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Mastering Android Dev (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 13,
            copiesAvailable = 6,
            language = "English",
            isReferenceOnly = false
        )
    )

}