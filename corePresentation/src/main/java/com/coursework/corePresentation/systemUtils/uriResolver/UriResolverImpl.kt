package com.coursework.corePresentation.systemUtils.uriResolver

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

internal class UriResolverImpl(
    private val context: Context
) : UriResolver {

    override fun getFileName(uri: Uri): String {
        var name: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) name = it.getString(index)
            }
        }
        return name ?: "file_${System.currentTimeMillis()}"
    }
}