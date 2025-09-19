package com.coursework.data

import com.coursework.domain.model.Book

object MockData {

    val books = listOf(
        Book(
            id = 11L,
            title = "Kotlin Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Josh Skeen", "David Greenhalgh"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true
        ),
        Book(
            id = 12L,
            title = "Android Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Bill Phillips", "Chris Stewart", "Kristin Marsicano"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true
        ),
        Book(
            id = 13L,
            title = "Pro Android 12",
            subtitle = "Developing Modern Mobile Apps",
            authors = listOf("Dave MacLean", "Satya Komatineni", "Sanjay Singh"),
            publisher = "Apress",
            hasPdfVersion = false
        ),
        Book(
            id = 14L,
            title = "Android Jetpack Compose Essentials",
            subtitle = null,
            authors = listOf("Neil Smyth"),
            publisher = "TechPress",
            hasPdfVersion = true
        ),
        Book(
            id = 15L,
            title = "Programming Android",
            subtitle = "Kotlin Edition",
            authors = listOf("Brett McLaughlin"),
            publisher = "O'Reilly Media",
            hasPdfVersion = false
        ),
        Book(
            id = 16L,
            title = "Head First Android Development",
            subtitle = "A Brain-Friendly Guide",
            authors = listOf("Dawn Griffiths", "David Griffiths"),
            publisher = "O'Reilly Media",
            hasPdfVersion = true
        ),
        Book(
            id = 17L,
            title = "Effective Kotlin",
            subtitle = "Best Practices for Kotlin Development",
            authors = listOf("Marcin Moskala"),
            publisher = "Leanpub",
            hasPdfVersion = true
        ),
        Book(
            id = 18L,
            title = "Kotlin Coroutines by Tutorials",
            subtitle = null,
            authors = listOf("Raywenderlich Tutorial Team"),
            publisher = "Raywenderlich",
            hasPdfVersion = true
        ),
        Book(
            id = 19L,
            title = "Android Clean Code",
            subtitle = null,
            authors = listOf("Robert C. Martin", "Android Dev Team"),
            publisher = "Prentice Hall",
            hasPdfVersion = false
        ),
        Book(
            id = 20L,
            title = "Mastering Android Development",
            subtitle = null,
            authors = listOf("John Horton"),
            publisher = "Packt Publishing",
            hasPdfVersion = true
        )
    )


}