package com.coursework.corePresentation.systemUtils.externalActivityLauncher

import android.net.Uri

interface ExternalActivityLauncher {

    object ResultCodes {
        const val UnknownError = -1
        const val Success = 0
        const val NoActivityFound = 1
    }

    fun launchPdfViewer(pdfUri: Uri): Int
}