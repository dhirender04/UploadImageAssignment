package com.app.uploadimageassignment.model

data class UploadImageDataModel(
    val error: Boolean,
    val message: String,
    val result: Result,
    val statusCode: Int,
    val success: Boolean
)