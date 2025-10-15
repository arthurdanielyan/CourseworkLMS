package com.coursework.data.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.net.toUri

class AndroidDownloader(
    context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(
        url: String,
        mimeType: String,
        title: String
    ): Long {
        val query = DownloadManager.Query()
        query.setFilterByStatus(DownloadManager.STATUS_PENDING or DownloadManager.STATUS_RUNNING)

        val cursor = downloadManager.query(query)
        while (cursor.moveToNext()) {
            val uri =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI).coerceAtLeast(0))
            if (uri == url) {
                // already downloading
                cursor.close()
                return -1
            }
        }
        cursor.close()

        // Get file extension
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: url.substringAfterLast('.', missingDelimiterValue = "")
                .substringBefore('?')

        val safeTitle = sanitizeFileName(title)

        // Add extension if not already included
        val fileName = if (safeTitle.endsWith(".$extension") || extension.isEmpty()) {
            safeTitle.substring(0, 35)
        } else {
            "$safeTitle.$extension"
        }

        val request = DownloadManager.Request(url.toUri())
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(title)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        return downloadManager.enqueue(request)
    }

    private fun sanitizeFileName(name: String): String {
        return name.replace(Regex("[\\\\/:*?\"<>|%#&+]"), "-")
    }
}