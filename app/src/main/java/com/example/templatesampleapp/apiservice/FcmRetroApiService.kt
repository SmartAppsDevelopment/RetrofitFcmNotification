package com.example.templatesampleapp.apiservice

import com.example.templatesampleapp.helper.Constants
import com.example.templatesampleapp.model.NotificationRequest
import com.example.templatesampleapp.model.sendreq.SendNotificationModel
import com.example.templatesampleapp.model.senreqtopic.SendTopicNotificationModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * @author Umer Bilal
 * Created 10/25/2023 at 10:31 AM
 */
interface FcmRetroApiService {
    //AIzaSyC1Ufhp0yqVD0405O2no3B11KsPRaKdlNg

    @Headers(
        "Content-Type:application/json"
    )
    @POST("sampleprojectstest-b9d22/messages:send")
    fun sendNotification(@Header("Authorization") bearerToken:String,@Body requestBody: SendNotificationModel): Call<ResponseBody>


    @POST("{project_id}/messages:send")
    fun sendNotificationToTopic(@Path(value = "project_id") project_id:String, @Header("Authorization") bearerToken:String, @Body requestBody: SendTopicNotificationModel): Call<ResponseBody>
//    @POST("fcm/send")
//    fun sendNotification(@Body request: NotificationRequest): Call<Void>

}