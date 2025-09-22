package com.coursework.data.downloader

interface Downloader {

    fun downloadFile(
        url: String,
        mimeType: String,
        title: String
    ): Long
}