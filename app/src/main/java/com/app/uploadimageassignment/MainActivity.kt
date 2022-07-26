package com.app.uploadimageassignment

import android.Manifest
import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.app.uploadimageassignment.network.ApiInterface
import com.app.uploadimageassignment.network.RetrofitClient
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import java.io.File


class MainActivity : AppCompatActivity() {
//    var loginSigupRepository: ImageUploadRepository = ImageUploadRepository(Application())
    val PERMISSION_REQUEST_CODE = 200
    var file: File? = null

    //    var imgeUrl: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        uploadButton.setOnClickListener {
            imageCapture()
        }
        uploadButton2.setOnClickListener {
            uploadImageApi()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(this) // optional usage
            Log.e("rwerwerewrewr", uriContent.toString())
            Log.e("rwerwerewrewr22525", uriFilePath.toString())
            file = File(uriFilePath.toString())
            Picasso.get().load(uriContent).into(ivImgHolder)
        } else {
            // an error occurred
            val exception = result.error
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun imageCapture() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )
    }

    private fun uploadImageApi() {
        Log.e("checkfileisempty",file.toString())
        val field = ArrayList<MultipartBody.Part>()
        getMultipartString("type", "media")?.let { field.add(it) }
        if (file != null) {
            getMultiPart("image", file!!)?.let { field.add(it) }
        }
        Log.e("65555555555555555",field.toString())
        val uploadapi = RetrofitClient.getInstance().create(ApiInterface::class.java)
        GlobalScope.launch {
           val result =  uploadapi.uploadImg(field)
            Log.e("resultnowdat87878788a", result.toString())
            if (result != null) {
                 Log.e("resultnowdata", result.toString())
            }
        }

//
////        val retrofitInstance = RetrofitClient.retrofit?.create(ApiInterface::class.java)
//        GlobalScope.launch {
//            loginSigupRepository.updateprofile(this@MainActivity, field)
//                .observe(this@MainActivity, {
//                    if (it.success) {
//                        when (it.statusCode) {
//                            200 -> {
//                                val gson = Gson()
//                                val json = gson.toJson(it)
//                                Log.e("3rwerewrwerew",json.toString())
//                            }
//                        }
//
//                    }
//                })
////          val result =   retrofitInstance?.uploadImg(field)
////            if (result != null){
////            Log.e("343434343423423",result.toString())
////            }
//        }


    }

    private fun getMultipartString(key: String?, file: String?): MultipartBody.Part? {
        return MultipartBody.Part.createFormData(key!!, file!!)

    }

    private fun getMultiPart(key: String?, file: File): MultipartBody.Part? {
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key!!, file.name, requestFile)
    }
}