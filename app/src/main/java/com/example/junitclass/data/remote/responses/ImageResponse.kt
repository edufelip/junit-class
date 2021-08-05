package com.example.junitclass.data.remote.responses

data class ImageResponse (
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
    )