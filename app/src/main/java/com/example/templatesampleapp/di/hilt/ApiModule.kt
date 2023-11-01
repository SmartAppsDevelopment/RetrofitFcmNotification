package com.example.templatesampleapp.di.hilt


import android.content.Context
import androidx.room.Room
import com.example.templatesampleapp.apiservice.FcmRetroApiService
import com.example.templatesampleapp.helper.Constants.BASE_URL
import com.example.templatesampleapp.helper.getChuckerInterceptor
import com.example.templatesampleapp.model.roomdb.NotificationModelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @HttpLoggerInterceptorBasic
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }


    @HttpLoggerInterceptorBody
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor1(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @HttpLoggerInterceptorHeader
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor2(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }


    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context,@HttpLoggerInterceptorHeader httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(getChuckerInterceptor(context))
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FcmRetroApiService = retrofit.create(FcmRetroApiService::class.java)


    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,
            NotificationModelDatabase::class.java, "Notifications"
        ).allowMainThreadQueries().build()


}