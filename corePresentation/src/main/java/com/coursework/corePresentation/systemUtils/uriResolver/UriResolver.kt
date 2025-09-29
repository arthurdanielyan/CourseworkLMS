package com.coursework.corePresentation.systemUtils.uriResolver

import android.net.Uri

interface UriResolver {

    fun getFileName(uri: Uri): String
}