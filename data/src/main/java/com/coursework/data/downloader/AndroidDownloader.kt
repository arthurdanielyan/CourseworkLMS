package com.coursework.data.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
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

        val request = DownloadManager.Request(url.toUri())
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(title)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)

        return downloadManager.enqueue(request)
    }
}