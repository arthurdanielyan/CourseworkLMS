package com.coursework.lms

import android.app.Application
import com.coursework.corePresentation.navigation.di.navigationModule
import com.coursework.corePresentation.systemUtils.di.systemUtils
import com.coursework.data.di.dataModule
import com.coursework.domain.di.domainModule
import com.coursework.featureBookDetails.di.featureBookDetailsModule
import com.coursework.featureEditBook.di.featureEditBookModule
import com.coursework.featureHome.di.homeModule
import com.coursework.featureSearchBooks.di.featureSearchBooksModule
import com.coursework.featurelogin.di.featureLoginModule
import com.coursework.utils.stringProvider.stringProviderModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IMSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                stringProviderModule,
                navigationModule,
                dataModule,
                domainModule,
                systemUtils,
                homeModule,
                featureLoginModule,
                featureSearchBooksModule,
                featureBookDetailsModule,
                featureEditBookModule,
            )
        }
    }
}