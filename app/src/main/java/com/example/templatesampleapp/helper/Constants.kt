package com.example.templatesampleapp.helper

import android.content.Context

object  Constants {

    val TEST_DEVICE_TOKEN="dwbMHcX3QfqwayOhnYxSld:APA91bEZrvJsKefCOwzhBnrGwxKBqkFlL_eh6B8j0oJZDws-4xP3i-5GWKy0_uBtmh1wrxbdV2Th0Xq4Mifw66GRACtJcpDsgk7S2yjYQIjQq-3SqOg5zAF04Vn_QEWebpo5ofZ4Lj5u"
//    const val TOKEN="AAAAABGPjbc:APA91bHpMYbYusZeGwjw7fEmLTQczZ17mS26Ioa7h7xHZvBR96e1wJ-dcmTkmUjTdyHDNa_KVTizgMvcFqhBk-xl1d_c4CRc7BaIodQZFmde9vSWxdkULdsRhgiEwgviFxKlaWTZcUEJ"

    val BASE_URL="https://fcm.googleapis.com/v1/projects/"

    val API_SCOPES="https://www.googleapis.com/auth/firebase.messaging"

    const val PROJECT_NAME=""



    object PrefKEY{
        val TOKEN="token"

        val Topic="topic"
        val title="title"
        val body="body"
    }

    fun setCustomKey(context: Context,key:String,value:String){
        context.getSharedPreferences("pref",Context.MODE_PRIVATE).edit().putString(key,value).apply()
    }

    fun getCustomKey(context: Context,key:String,default:String?=null):String?{
        return context.getSharedPreferences("pref",Context.MODE_PRIVATE).getString(key,default)
    }

    fun setToken(context: Context,token:String?){
        context.getSharedPreferences("pref",Context.MODE_PRIVATE).edit().putString("token",token).apply()
    }

    fun getToken(context: Context):String?{
        return context.getSharedPreferences("pref",Context.MODE_PRIVATE).getString("token",null)
    }

    fun getBearerToken(context: Context):String?{
        return context.getSharedPreferences("pref",Context.MODE_PRIVATE).getString("token",null)?.let {
            "Bearer $it"
        }
    }





    fun setListOfTopics(context: Context,token:HashSet<String>?){
        context.getSharedPreferences("pref",Context.MODE_PRIVATE).edit().putStringSet("topiclist",token).apply()
    }

    fun getListOfTopics(context: Context):Set<String>?{
        return context.getSharedPreferences("pref",Context.MODE_PRIVATE).getStringSet("topiclist",null)
    }
}