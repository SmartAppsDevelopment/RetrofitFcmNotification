package com.example.templatesampleapp.ui.activmain

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.templatesampleapp.apiservice.FcmRetroApiService
import com.example.templatesampleapp.base.BaseViewModel
import com.example.templatesampleapp.helper.Constants
import com.example.templatesampleapp.model.NotificationRequest
import com.example.templatesampleapp.model.SampleItem
import com.example.templatesampleapp.model.roomdb.NotificationModelDatabase
import com.example.templatesampleapp.model.roomdb.NotificationRoomDbModel
import com.example.templatesampleapp.model.sendreq.Message
import com.example.templatesampleapp.model.sendreq.Notification
import com.example.templatesampleapp.model.sendreq.SendNotificationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(val fcmRetroApiService: FcmRetroApiService,val db: NotificationModelDatabase) : ViewModel() {


    var listOfNotificatiosn: MutableLiveData<List<NotificationRoomDbModel>> = MutableLiveData(emptyList())


    val TAG="MainViewModel"
    var listOfItems: MutableLiveData<List<SampleItem>> = MutableLiveData(emptyList())
    fun getData(): MutableLiveData<List<SampleItem>> {
        listOfItems.value =
            return listOfItems
//             MutableLiveData<List<SampleItem>>().apply {
//
//             }
    }
//             flow<List<SampleItem>> {
//
//                emit(listOfItems)
//            }.toList()


    fun sendNotifiation(context: Context,mainActivity: MainActivity) {



        Log.e(TAG, "sendNotifiation: ", )
        fcmRetroApiService
            .sendNotification(Constants.getBearerToken(context)?:"",
                SendNotificationModel(Message(Notification("FromMobileBody","FromMobile"),Constants.TEST_DEVICE_TOKEN))
            )
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "onResponse: "+response.code() )
                    mainActivity.appendLog("Code ",response.code().toString())
                    mainActivity.appendLog("raw ",response.raw().toString())
                    mainActivity.appendLog("body ",response.body().toString())
                    mainActivity.appendLog("msg ",response.message())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "onFailure: "+t.message )
                    mainActivity.appendLog("Fail ",t.message)

                }
            })
//            .enqueue(object : Callback<Void>{
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    Log.e(TAG, "onResponse: ", )
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e(TAG, "onFailure: "+t.message )
//                }
//            })
    }


//    fun getNotifaciotnsList


    fun insertNotificationsItem(notificationModelDatabase: NotificationRoomDbModel){

        db.userDao().insertAll(notificationModelDatabase)
    }



}