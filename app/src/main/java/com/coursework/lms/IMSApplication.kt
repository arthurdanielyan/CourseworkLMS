package com.coursework.lms

import android.app.Application
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.NavControllersHolder
import com.coursework.corePresentation.systemUtils.di.systemUtils
import com.coursework.corePresentation.viewState.books.di.coreBooksModule
import com.coursework.data.di.dataModule
import com.coursework.domain.di.domainModule
import com.coursework.featureBookDetails.di.featureBookDetailsModule
import com.coursework.featureEditBook.di.featureEditBookModule
import com.coursework.featureHome.di.homeModule
import com.coursework.featureSearchBooks.booksList.di.booksListModule
import com.coursework.featureSearchBooks.searchFilters.di.searchFiltersModule
import com.coursework.featureSearchBooks.shared.di.searchBooksSharedModule
import com.coursework.featurelogin.di.featureLoginModule
import com.coursework.utils.stringProvider.stringProviderModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class IMSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                stringProviderModule,
                module {
                    val appRouter = AppRouterImpl()
                    single<AppRouter> {
                        appRouter
                    }
                    single<NavControllersHolder> {
                        appRouter
                    }
                },
                dataModule,
                domainModule,
                systemUtils,
                coreBooksModule,
                homeModule,
                featureLoginModule,
                booksListModule,
                searchFiltersModule,
                searchBooksSharedModule,
                featureBookDetailsModule, // TODO: remove "feature" prefixes
                featureEditBookModule,
            )
        }
    }
}