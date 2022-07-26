//package com.app.uploadimageassignment
//
//import android.app.Activity
//import android.app.Application
//import androidx.lifecycle.MutableLiveData
//import com.app.uploadimageassignment.model.UploadImageDataModel
//import com.app.uploadimageassignment.network.ApiInterface
//import com.app.uploadimageassignment.network.RetrofitClient
//import okhttp3.MultipartBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.ArrayList
//
//class ImageUploadRepository(private val baseActivity: Application) {
//    var retrofitClient: ApiInterface? = null
//    val signupmutableLiveData = MutableLiveData<UploadImageDataModel>()
//   suspend fun updateprofile(baseActivity: Activity, fields: ArrayList<MultipartBody.Part>): MutableLiveData<UploadImageDataModel>
//    {
//
//            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
//                ApiInterface::class.java
//            )
//            retrofitClient?.uploadImg(fields)!!.enqueue(object :
//                Callback<UploadImageDataModel>
//            {
//                override fun onResponse(
//                    call: Call<UploadImageDataModel?>,
//                    response: Response<UploadImageDataModel?>
//                ) {
//                    when(response.code())
//                    {
//                        200->{
//                            signupmutableLiveData.setValue(response.body())
//                        }
//                        412->{
//
//                        }
//                        401->{
//
//                        }
//                    }
//
//                }
//
//                override fun onFailure(call: Call<UploadImageDataModel?>, t: Throwable)
//                {
//                    signupmutableLiveData.setValue(null)
//                }
//            })
//
//        return signupmutableLiveData
//    }
//}