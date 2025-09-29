package com.coursework.corePresentation.systemUtils.di

import com.coursework.corePresentation.systemUtils.externalActivityLauncher.ExternalActivityLauncher
import com.coursework.corePresentation.systemUtils.externalActivityLauncher.ExternalActivityLauncherImpl
import com.coursework.corePresentation.systemUtils.uriResolver.UriResolver
import com.coursework.corePresentation.systemUtils.uriResolver.UriResolverImpl
import org.koin.dsl.module

val systemUtils = module {
    factory<UriResolver> {
        UriResolverImpl(
            context = get()
        )
    }

    factory<ExternalActivityLauncher> {
        ExternalActivityLauncherImpl(
            context = get()
        )
    }
}