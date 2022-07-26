package com.app.uploadimageassignment.network

import com.app.uploadimageassignment.model.UploadImageDataModel

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Multipart
    @POST("cid=7ec99b415af3e88205250e3514ce0fa")
    suspend fun uploadImg(
        @Part fields: ArrayList<MultipartBody.Part>,
    ): Call<UploadImageDataModel>
}