package com.coursework.corePresentation.systemUtils.externalActivityLauncher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

internal class ExternalActivityLauncherImpl(
    private val context: Context
) : ExternalActivityLauncher {

    override fun launchPdfViewer(pdfUri: Uri): Int {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        try {
            context.startActivity(intent)
            return ExternalActivityLauncher.ResultCodes.Success
        } catch (_: ActivityNotFoundException) {
            return ExternalActivityLauncher.ResultCodes.NoActivityFound
        } catch (_: Throwable) {
            return ExternalActivityLauncher.ResultCodes.UnknownError
        }
    }
}